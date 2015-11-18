/*
 * Copyright (C) 2015 Pierre-Yves Lajoie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package network_frames;
import java.util.Stack;
import java.util.LinkedList;
import network_components.DataLink;
/**
 *
 * @author Pierre-Yves Lajoie
 * @creationDate    October 7th 2015
 * @lastUpdate      November 18th 2015
 * 
 */
public class RCFrame implements Frame{
        
    /*Constructors*/
    public RCFrame(int ID, double C, double Offset, double Rate, DataPath DP){
        id=ID;
        c=C;
        offset=Offset;
        t0=Offset;
        tj=Offset;
        dp=DP;
        rate=Rate;
    }
    public RCFrame(int ID, double C, double Offset, double Rate){
        
        id = ID;
        c=C;
        offset=Offset;
        t0=Offset;
        rate=Rate;
        tj=Offset;
    }
    
    /*Attributes*/
    private int id;
    private double tj; //last beginning of transmission time
    private double c; //transimission delay
    private double offset; //start time
    private double t0; //initial offset
    private double rate; //initial offset
    
    private DataPath dp = new DataPath();
    
    /*Methods*/
    
    public void setTj(double Tj){
        tj=Tj;
    }
    public final double getTj(){
        return tj;
    }
    
    public void setC(double delay){
        c=delay;
    }
    public final double getC(){
        return c;
    }
    public void setOffset(double offs){
        offset=offs;
    }
    public final double getOffset(){
        return offset;
    }
    public void setDataPath(DataPath d){
        dp=d;
    }
    public final DataPath getDataPath(){
        return dp;
    }
    public final int getID(){
        return id;
    }
    public final double getT0(){
        return t0;
    }
    public final double getRate(){
        return rate;
    }
    public double initialisationStep(DataLink dlj){
            double busyPeriod = c;
            //Sommation of Cj of each RCFrame on the DataLink
            for(Frame frame : dlj.getRC_schedule().getFramesList()){
                if (frame!= this){ //if it is a another frame
                    if(frame.getOffset()>=offset)//if the RC frame is after the framex
                        busyPeriod += frame.getC();
                    else if(frame.getOffset()+frame.getC() > offset) //TODO: work since we know everything about other RC
                        busyPeriod += frame.getOffset()+frame.getC() - offset;
                }
            }
            tj=busyPeriod + offset;
            return busyPeriod;
    }
    
    public double computeDemand(DataLink dlj, double busyPeriod){
        //Hxj(bpxj)
        double demand = 0;
        //Sommation of Cj of each RCFrame other than current frame on the DataLink and consideration of fi.rate
            for(Frame frame : dlj.getRC_schedule().getFramesList()){
                    if((frame.getID() != id)&&(frame.getOffset()==offset))//TODO: >= or ==?
                        demand += frame.getC()* Math.ceil(busyPeriod/frame.getRate());
                    else if((frame.getID() != id)&&(frame.getOffset()+frame.getC() > offset)&&(frame.getOffset() < offset)) // work since we know everything about other RC
                        demand += frame.getOffset()+frame.getC() - offset* Math.ceil(busyPeriod/frame.getRate());
                
            }
            //Addition of Cxj which is equal to Cx because Cx is considered as constant
            demand += c;
         
         return demand;
        
    }
    
    public double computeAvailibility(DataLink dlj, double busyPeriod){
        //Axj(bpxj)
        double availibility = 0;
        availibility = tj;
            //Computing of Qtt
            double Qtt = computeQtt(dlj, busyPeriod);
            
            //Computing Qtb
            double Qtb =computeQtb(dlj, busyPeriod);
            
            
            double Qtl = computeQtl();//TODO: Fix Qtl. For now,it is estimated to 0.
            
            //compute of availibility
            availibility = busyPeriod - (Qtt + Qtb + Qtl);
            //System.out.print("Qtt : "+ Qtt +"\n");//test
            //System.out.print("Qtb : "+ Qtb +"\n");//test
            return availibility;
    }
    
    public double computeQtt(DataLink dlj, double busyPeriod){
        double Qtt = 0;
            for(Frame frame : dlj.getTT_schedule().getFramesList()){ 
                //There is 4 cases
                //if the TT frame is totally between tc and tc+busyPeriod
                if(frame.getOffset()>= offset && frame.getOffset()+frame.getC() <= offset+busyPeriod)
                    Qtt += frame.getC();
                //if the TT begins but not finish in the time interval
                else if(frame.getOffset()>= offset && frame.getOffset() <= offset+busyPeriod && frame.getOffset() + frame.getC()>offset+busyPeriod) 
                    Qtt += offset + busyPeriod - frame.getOffset();
                //if the TT starts before the time interval and finish during it.
                else if(frame.getOffset() < offset && frame.getOffset()+frame.getC() <= offset+busyPeriod && frame.getOffset()+frame.getC() > offset) 
                    Qtt += frame.getOffset() + frame.getC() - offset;
                //if the TT covers totally the time interval.
                else if(frame.getOffset() < offset && frame.getOffset()+frame.getC() > offset+busyPeriod)  
                    Qtt += busyPeriod;
            }
        return Qtt;
    }
    
    public double findLargestC(DataLink dlj){ //TODO: merge find the largest and the smallest
        double largestC = c;
        //find the largestC 
                for(Frame frame : dlj.getRC_schedule().getFramesList()){
                        if(frame.getC() > largestC)
                            largestC = frame.getC();
                }
         return largestC;
    }
    
    public double findSmallestC(DataLink dlj){
        double smallestC = c;
        //find the smallestC
                for(Frame frame : dlj.getRC_schedule().getFramesList()){
                        if(frame.getC() < smallestC)
                            smallestC = frame.getC();
                }
         return smallestC;
    }
    
    public double computeQtb(DataLink dlj, double busyPeriod){
        //Computing Qtb
            double Qtb = 0;
            //Selection of the largest C and save the value of the smallest Camong the RC frames
            double largestC, smallestC;
            boolean isTooSmall = false;//TODO: can it happen  just once?
            double tooSmallGap = 0;
            //to see if there a too small gap for RC to pass. We use it for the availibility.
            if (!dlj.getTT_schedule().getFramesList().isEmpty()){
                
                largestC = findLargestC(dlj);
                smallestC = findSmallestC(dlj);
                    
                //Time block by TT frames
                double timeBlockByTT = 0;
                double timeBetween2TTFrames;
                Frame previousTTFrame=dlj.getTT_schedule().getFramesList().getFirst();
                for(Frame frame : dlj.getTT_schedule().getFramesList()){ 
                    //Computing the time between TT.
                    if (previousTTFrame==frame)
                        timeBetween2TTFrames = Double.MAX_VALUE;
                    else
                        timeBetween2TTFrames = frame.getOffset()-(previousTTFrame.getOffset() + previousTTFrame.getC());
                    //There is 4 cases
                    //if the next TT begins before offset()+largestC //TODO
                    if(frame.getOffset()>= offset && frame.getOffset() <= offset+ largestC){
                        timeBlockByTT += frame.getOffset()-offset;
                    }
                    //if the time block is totally between tc and tc+busyPeriod and is smaller than the gap between the 2 TT frames
                    else if(frame.getOffset()>= offset+largestC && frame.getOffset()<= offset+busyPeriod && largestC<= timeBetween2TTFrames){
                        //if there is not enough time to pass a RCFrame. 
                        //Verification that there is a big enough continous availibility to pass frame.
                        double rcSatisfaction =0;
                        for(Frame rc : dlj.getRC_schedule().getFramesList()){//TODO: Reduce complexity if possible
                            if(!isTooSmall){
                                if(timeBetween2TTFrames-largestC-rcSatisfaction >= rc.getC()){
                                    rcSatisfaction += rc.getC();
                                }
                                else{
                                    tooSmallGap += timeBetween2TTFrames-largestC - rcSatisfaction;
                                    isTooSmall = true;
                                }
                            }
                        }
                        timeBlockByTT += largestC;
                    }
                    //if the TT starts after the time interval and the previous TT starts in the interval but finish after frame.offset()-largestC.
                    else if(frame.getOffset() >= offset + busyPeriod && previousTTFrame.getOffset() >= offset
                            && previousTTFrame.getOffset() + previousTTFrame.getC() <= offset+busyPeriod 
                            && previousTTFrame.getOffset() + previousTTFrame.getC() >= frame.getOffset()-largestC ) {
                        timeBlockByTT += frame.getOffset() + busyPeriod - (previousTTFrame.getOffset() + previousTTFrame.getC());
                    }
                    //if the TT starts outside of the time interval but has an impact in the time blocking.
                    else if(frame.getOffset() > offset + busyPeriod && frame.getOffset()-largestC < offset+busyPeriod){
                        //if there is not enough time to pass a RCFrame. 
                        //Verification that there is a big enough continous availibility to pass frame.
                        double rcSatisfaction = 0;
                        for(Frame rc : dlj.getRC_schedule().getFramesList()){//TODO: Reduce complexity if possible
                            if(!isTooSmall){
                                if(timeBetween2TTFrames-largestC-rcSatisfaction >= rc.getC()){
                                    rcSatisfaction += rc.getC();
                                }
                                else{
                                    tooSmallGap += timeBetween2TTFrames-largestC - rcSatisfaction;
                                    isTooSmall = true;
                                }
                            }
                        }
                        timeBlockByTT += offset+busyPeriod - (frame.getOffset()-largestC);
                    }
                    //Saving the TTframe
                    previousTTFrame=frame;
                
                    //Selection of the smallest Qtb
                    if(largestC < timeBlockByTT)
                        Qtb += largestC;
                    else
                        Qtb += timeBlockByTT;
                    
                    //reinitialisation of timeBlockByTT
                    timeBlockByTT=0;
                }
            }//ELSE: since there is no TT frames, Qtb is 0
            //if the availibility is not enough for even the smallest frame and the busyPeriod ends with a time block, we ignore it
            if(isTooSmall){
                Qtb+=tooSmallGap;
            }
        return Qtb;
    }
    
    public double computeQtl(){
        return 0;
    }
    
    public double computeBusyPeriod(DataLink dlj){
        
        /*
         *       Initialisation
         */
        double busyPeriod = initialisationStep(dlj);
        
        double demand;
        double availibility;
        do{
            //System.out.print("FrameID : "+ id + " DLID : "+dlj.getID()+"\n");//test
            /*
                Compute demand Hxj(bpxj)
            */
            demand = computeDemand(dlj, busyPeriod);
            //System.out.print("Demand : "+ demand +"\n");//test
            /* 
                Compute availability Axj(bpxj)
            */
            availibility = computeAvailibility(dlj, busyPeriod);
            //System.out.print("Availibility : "+ availibility+"\n");//test
            if(demand > availibility)
                tj = tj+ demand - availibility;
            
            //Update the busyPeriod
            busyPeriod = tj - offset;
            //Infomation
            //System.out.print("Tj : "+ tj+"\n");//test
            //System.out.print("offset : "+ offset +"\n");//test
            //System.out.print("BusyPeriod : "+ busyPeriod +"\n\n");//test
        }while(availibility < demand);
        
        return busyPeriod;
    }
    
    public double computeDelayOnFrame(){
        double busyPeriod = 0;
        double delay = 0;
            //System.out.print("Frame "+id+" : "); //test
            for(DataLink dlj: dp.getDataLinksQueue()){
                
                /*
                * Time Issue: We can't go back in time so the offset of current frame is bigger or equal to the Tj of the previous 
                */
                for(Frame frameRC : dlj.getRC_schedule().getFramesList()){
                    if (frameRC == this)
                        break;
                    if(offset < tj){
                        offset = tj;
                        tj= tj + c;
                    }
                }
                //Verification that the frame does not start during a TT frame
                for(Frame frameTT : dlj.getTT_schedule().getFramesList()){
                    if(offset > frameTT.getOffset() && offset < frameTT.getTj()){
                        offset = frameTT.getTj();
                        tj = frameTT.getTj()+ c;
                    }
                }
                
                
                //computing of busyPeriod
                busyPeriod = computeBusyPeriod(dlj);
                //computing of tn
                tj= offset + busyPeriod;
                
                //updating of tc
                offset = offset + busyPeriod - c;

                //info
                /*System.out.print(offset);//test
                System.out.print("|--");//test
                System.out.print(dlj.getID()); //test
                System.out.print("--|");//test
                System.out.print(tj+"       ");//test*/
                //System.out.print("BP: "+busyPeriod+"   "); //test
                
            }
            //System.out.print("\n\n");//test
            
            delay = tj-t0;
            
            //Reset offset and t of frame
            offset = t0;
            tj = t0;
        return delay;
    }
    
    public static double computeDelay(LinkedList<RCFrame> RCFrameQueue){
        double delay = 0;
        //for dp elements of virtual link.
        for(RCFrame frame: RCFrameQueue){
            double delayOnFrame = frame.computeDelayOnFrame();
            if (delay < delayOnFrame)
                delay = delayOnFrame;
        }
        return delay;
    }
}
