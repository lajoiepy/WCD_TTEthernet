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

/**
 *
 * @author Pierre-Yves Lajoie
 * @creationDate    October 7th 2015
 * @lastUpdate      November 9th 2015
 * 
 */
public class TTFrame implements Frame{
    
    /*Constructor*/
    public TTFrame(int ID, double C, double Offset, double Rate, DataPath DP){
        c=C;
        offset=Offset;
        dp=DP;
        id = ID;
        rate=Rate;
        t=Offset+C;
    }
    public TTFrame(int ID, double C, double Offset, double Rate){
        id = ID;
        c=C;
        offset=Offset;
        rate=Rate;
        t=Offset+C;
    }
    /*Attributes*/
    private int id;
    private double c; //transimission delay
    private double offset; //start time
    private double rate;
    private double t;
    
    private DataPath dp = new DataPath();
    
    /*Methods*/
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
    public final double getRate(){
        return rate;
    }
    public final double getTj(){
        return t;
    }
}
