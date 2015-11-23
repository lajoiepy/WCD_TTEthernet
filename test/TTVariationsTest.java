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

import network_components.DataLink;
import network_components.EndSystem;
import network_components.NetworkNode;
import network_frames.DataPath;
import network_frames.RCFrame;
import network_frames.TTFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pierre-Yves Lajoie
 * @creationDate    November 14th 2015
 * @lastUpdate      November 22th 2015
 */
public class TTVariationsTest {
    //Tests
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT1_1(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //TT1 moved to 2ms, There is no changes in the WCD
        TTFrame tt1 = new TTFrame(1,3,2,32);
        dl1.getTT_schedule().add(tt1);
        TTFrame tt2 = new TTFrame(2,2.5,9,32);
        dl1.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().add(rc2);
        //Frame 12
        dp12.getDataLinksQueue().add(dl1);
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 16.5ms",(busyPeriod == 16.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT1_2(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //TT1 moved to 6ms, so the f11 goes before TT1 and TT2.
        TTFrame tt1 = new TTFrame(1,3,6,32);
        dl1.getTT_schedule().add(tt1);
        TTFrame tt2 = new TTFrame(2,2.5,9,32);
        dl1.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().add(rc2);
        //Frame 12
        dp12.getDataLinksQueue().add(dl1);
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 14ms",(busyPeriod == 14));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT2_1(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt1 = new TTFrame(1,3,3,32);
        dl1.getTT_schedule().add(tt1);
        //TT2 moved to 10ms, so the frames ar shifted by one
        TTFrame tt2 = new TTFrame(2,2.5,10,32);
        dl1.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().add(rc2);
        //Frame 12
        dp12.getDataLinksQueue().add(dl1);
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 17.5ms",(busyPeriod == 17.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT2_2(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt1 = new TTFrame(1,3,3,32);
        dl1.getTT_schedule().add(tt1);
        //TT2 moved to 12.5ms, so f10 and f11 goes between TT1 and TT2.
        TTFrame tt2 = new TTFrame(2,2.5,12.5,32);
        dl1.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().add(rc2);
        //Frame 12
        dp12.getDataLinksQueue().add(dl1);
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 17.5ms",(busyPeriod == 17.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT2_3(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt1 = new TTFrame(1,3,3,32);
        dl1.getTT_schedule().add(tt1);
        //TT2 moved to 20ms, so f10, f11 and f12 goes between TT1 and TT2.
        TTFrame tt2 = new TTFrame(2,2.5,20,32);
        dl1.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().add(rc2);
        //Frame 12
        dp12.getDataLinksQueue().add(dl1);
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 11ms",(busyPeriod == 11));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT2_4(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt1 = new TTFrame(1,3,3,32);
        dl1.getTT_schedule().add(tt1);
        //TT2 moved to 14ms, so f11 and f12 goes between TT1 and TT2.
        TTFrame tt2 = new TTFrame(2,2.5,14,32);
        dl1.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().add(rc2);
        //Frame 12
        dp12.getDataLinksQueue().add(dl1);
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 17ms",(busyPeriod == 17));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT1_TT2_1(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //Swap the 2 TT frames
        TTFrame tt2 = new TTFrame(2,2.5,3,32);
        dl1.getTT_schedule().add(tt2);
        TTFrame tt1 = new TTFrame(1,3,9,32);
        dl1.getTT_schedule().add(tt1);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().add(rc2);
        //Frame 12
        dp12.getDataLinksQueue().add(dl1);
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 17ms",(busyPeriod == 17));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT1_TT2_2(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //The 2 TTs are shift by 2
        TTFrame tt1 = new TTFrame(1,3,5,32);
        dl1.getTT_schedule().add(tt1);
        TTFrame tt2 = new TTFrame(2,2.5,11,32);
        dl1.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().add(rc2);
        //Frame 12
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().add(f12);
        dp12.getDataLinksQueue().add(dl1);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 18.5ms",(busyPeriod == 18.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL2_f12_TT3_1(){
        //Node of dl2
        NetworkNode NS1 = new NetworkNode("NS1");
        NetworkNode NS2 = new NetworkNode("NS2"); 
        //DataLink and DataPath
        DataLink dl2 = new DataLink(NS1, NS2,2);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt2 = new TTFrame(1,3,7,32);
        dl2.getTT_schedule().add(tt2);
        //TT3 moved to 15
        TTFrame tt3 = new TTFrame(3,3.5,15,32);
        dl2.getTT_schedule().add(tt3);
         //Schedule of RC frames
        RCFrame rc3 = new RCFrame(13,2,13.5,32);
        dl2.getRC_schedule().add(rc3);
        RCFrame rc4 = new RCFrame(14,1.5,13.5,32);
        dl2.getRC_schedule().add(rc4);
        RCFrame rc5 = new RCFrame(10,1.5,13.5,32);
        dl2.getRC_schedule().add(rc5);
        RCFrame rc6 = new RCFrame(11,2.5,13.5,32);
        dl2.getRC_schedule().add(rc6);
        //Frame 12
        dp12.getDataLinksQueue().add(dl2);
        RCFrame f12 = new RCFrame(12,2,13.5,32,dp12);
        dl2.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl2);
        assertTrue("Wrong result, calculated BusyPeriod for dl2 = "+ busyPeriod +"ms, expected: 14.5ms",(busyPeriod == 14.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL2_f12_TT3_2(){
        //Node of dl2
        NetworkNode NS1 = new NetworkNode("NS1");
        NetworkNode NS2 = new NetworkNode("NS2"); 
        //DataLink and DataPath
        DataLink dl2 = new DataLink(NS1, NS2,2);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt2 = new TTFrame(1,3,7,32);
        dl2.getTT_schedule().add(tt2);
        //TT3 moved to 17 so this is the limit without any frame passing before TT3
        TTFrame tt3 = new TTFrame(3,3.5,17,32);
        dl2.getTT_schedule().add(tt3);
         //Schedule of RC frames
        RCFrame rc3 = new RCFrame(13,2,13.5,32);
        dl2.getRC_schedule().add(rc3);
        RCFrame rc4 = new RCFrame(14,1.5,13.5,32);
        dl2.getRC_schedule().add(rc4);
        RCFrame rc5 = new RCFrame(10,1.5,13.5,32);
        dl2.getRC_schedule().add(rc5);
        RCFrame rc6 = new RCFrame(11,2.5,13.5,32);
        dl2.getRC_schedule().add(rc6);
        //Frame 12
        dp12.getDataLinksQueue().add(dl2);
        RCFrame f12 = new RCFrame(12,2,13.5,32,dp12);
        dl2.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl2);
        assertTrue("Wrong result, calculated BusyPeriod for dl2 = "+ busyPeriod +"ms, expected: 16.5ms",(busyPeriod == 16.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL2_f12_TT3_3(){
        //Node of dl2
        NetworkNode NS1 = new NetworkNode("NS1");
        NetworkNode NS2 = new NetworkNode("NS2"); 
        //DataLink and DataPath
        DataLink dl2 = new DataLink(NS1, NS2,2);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt2 = new TTFrame(1,3,7,32);
        dl2.getTT_schedule().add(tt2);
        //TT3 moved to 17.5 so one small could pass but none will in WCD
        TTFrame tt3 = new TTFrame(3,3.5,17.5,32);
        dl2.getTT_schedule().add(tt3);
         //Schedule of RC frames
        RCFrame rc3 = new RCFrame(13,2,13.5,32);
        dl2.getRC_schedule().add(rc3);
        RCFrame rc4 = new RCFrame(14,1.5,13.5,32);
        dl2.getRC_schedule().add(rc4);
        RCFrame rc5 = new RCFrame(10,1.5,13.5,32);
        dl2.getRC_schedule().add(rc5);
        RCFrame rc6 = new RCFrame(11,2.5,13.5,32);
        dl2.getRC_schedule().add(rc6);
        //Frame 12
        dp12.getDataLinksQueue().add(dl2);
        RCFrame f12 = new RCFrame(12,2,13.5,32,dp12);
        dl2.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl2);
        assertTrue("Wrong result, calculated BusyPeriod for dl2 = "+ busyPeriod +"ms, expected: 17ms",(busyPeriod == 17));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL2_f12_TT1_1(){
        //Node of dl2
        NetworkNode NS1 = new NetworkNode("NS1");
        NetworkNode NS2 = new NetworkNode("NS2"); 
        //DataLink and DataPath
        DataLink dl2 = new DataLink(NS1, NS2,2);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt3 = new TTFrame(3,3.5,12.5,32);
        dl2.getTT_schedule().add(tt3);
        TTFrame tt2 = new TTFrame(1,3,21,32);
        //TT1 moved to 21 so f11 could pass between the 2
        dl2.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc3 = new RCFrame(13,2,13.5,32);
        dl2.getRC_schedule().add(rc3);
        RCFrame rc4 = new RCFrame(14,1.5,13.5,32);
        dl2.getRC_schedule().add(rc4);
        RCFrame rc5 = new RCFrame(10,1.5,13.5,32);
        dl2.getRC_schedule().add(rc5);
        RCFrame rc6 = new RCFrame(11,2.5,13.5,32);
        dl2.getRC_schedule().add(rc6);
        //Frame 12
        dp12.getDataLinksQueue().add(dl2);
        RCFrame f12 = new RCFrame(12,2,13.5,32,dp12);
        dl2.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl2);
        assertTrue("Wrong result, calculated BusyPeriod for dl2 = "+ busyPeriod +"ms, expected: 17.5ms",(busyPeriod == 17.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL2_f12_TT1_2(){
        //Node of dl2
        NetworkNode NS1 = new NetworkNode("NS1");
        NetworkNode NS2 = new NetworkNode("NS2"); 
        //DataLink and DataPath
        DataLink dl2 = new DataLink(NS1, NS2,2);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt3 = new TTFrame(3,3.5,12.5,32);
        dl2.getTT_schedule().add(tt3);
        TTFrame tt2 = new TTFrame(1,3,20.5,32);
        //TT1 moved to 20.5 so none will pass between the TT frames in WCD
        dl2.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc3 = new RCFrame(13,2,13.5,32);
        dl2.getRC_schedule().add(rc3);
        RCFrame rc4 = new RCFrame(14,1.5,13.5,32);
        dl2.getRC_schedule().add(rc4);
        RCFrame rc5 = new RCFrame(10,1.5,13.5,32);
        dl2.getRC_schedule().add(rc5);
        RCFrame rc6 = new RCFrame(11,2.5,13.5,32);
        dl2.getRC_schedule().add(rc6);
        //Frame 12
        dp12.getDataLinksQueue().add(dl2);
        RCFrame f12 = new RCFrame(12,2,13.5,32,dp12);
        dl2.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl2);
        assertTrue("Wrong result, calculated BusyPeriod for dl2 = "+ busyPeriod +"ms, expected: 19.5ms",(busyPeriod == 19.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL2_f12_TT1_TT2_1(){
        //Node of dl2
        NetworkNode NS1 = new NetworkNode("NS1");
        NetworkNode NS2 = new NetworkNode("NS2"); 
        //DataLink and DataPath
        DataLink dl2 = new DataLink(NS1, NS2,2);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt3 = new TTFrame(3,3.5,14.5,32);
        dl2.getTT_schedule().add(tt3);
        TTFrame tt2 = new TTFrame(1,3,20.5,32);
        //TT1 shifted by 2 and TT2 moved to 20.5
        dl2.getTT_schedule().add(tt2);
         //Schedule of RC frames
        RCFrame rc3 = new RCFrame(13,2,13.5,32);
        dl2.getRC_schedule().add(rc3);
        RCFrame rc4 = new RCFrame(14,1.5,13.5,32);
        dl2.getRC_schedule().add(rc4);
        RCFrame rc5 = new RCFrame(10,1.5,13.5,32);
        dl2.getRC_schedule().add(rc5);
        RCFrame rc6 = new RCFrame(11,2.5,13.5,32);
        dl2.getRC_schedule().add(rc6);
        //Frame 12
        dp12.getDataLinksQueue().add(dl2);
        RCFrame f12 = new RCFrame(12,2,13.5,32,dp12);
        dl2.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl2);
        assertTrue("Wrong result, calculated BusyPeriod for dl2 = "+ busyPeriod +"ms, expected: 19.5ms",(busyPeriod == 19.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL6_f12_TT1_1(){
        //Node of dl6
        EndSystem ES4 = new EndSystem("ES4");
        NetworkNode NS2 = new NetworkNode("NS2");
        //DataLink and DataPath
        DataLink dl6 = new DataLink(ES4, NS2,6);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //TT1 moved to 22ms, so the frames are push back later
        TTFrame tt1 = new TTFrame(1,3,22,32);
        dl6.getTT_schedule().add(tt1);
         //Schedule of RC frames
        RCFrame rc7 = new RCFrame(13,2,23.5,32);
        dl6.getRC_schedule().add(rc7);
        //Frame 12
        dp12.getDataLinksQueue().add(dl6);
        RCFrame f12 = new RCFrame(12,2,23.5,32,dp12);
        dl6.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl6);
        assertTrue("Wrong result, calculated BusyPeriod for dl6 = "+ busyPeriod +"ms, expected: 5.5ms",(busyPeriod == 5.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL6_f12_TT1_2(){
        //Node of dl6
        EndSystem ES4 = new EndSystem("ES4");
        NetworkNode NS2 = new NetworkNode("NS2");
        //DataLink and DataPath
        DataLink dl6 = new DataLink(ES4, NS2,6);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //TT1 moved to 27ms, so the frames are push back after the frame due to Qtb
        TTFrame tt1 = new TTFrame(1,3,27,32);
        dl6.getTT_schedule().add(tt1);
         //Schedule of RC frames
        RCFrame rc7 = new RCFrame(13,2,23.5,32);
        dl6.getRC_schedule().add(rc7);
        //Frame 12
        dp12.getDataLinksQueue().add(dl6);
        RCFrame f12 = new RCFrame(12,2,23.5,32,dp12);
        dl6.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl6);
        assertTrue("Wrong result, calculated BusyPeriod for dl6 = "+ busyPeriod +"ms, expected: 10.5ms",(busyPeriod == 10.5));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL6_f12_TT1_3(){
        //Node of dl6
        EndSystem ES4 = new EndSystem("ES4");
        NetworkNode NS2 = new NetworkNode("NS2");
        //DataLink and DataPath
        DataLink dl6 = new DataLink(ES4, NS2,6);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //TT1 moved to 27.5ms, so one of the frames can pass before TT1
        TTFrame tt1 = new TTFrame(1,3,27.5,32);
        dl6.getTT_schedule().add(tt1);
         //Schedule of RC frames
        RCFrame rc7 = new RCFrame(13,2,23.5,32);
        dl6.getRC_schedule().add(rc7);
        //Frame 12
        dp12.getDataLinksQueue().add(dl6);
        RCFrame f12 = new RCFrame(12,2,23.5,32,dp12);
        dl6.getRC_schedule().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl6);
        assertTrue("Wrong result, calculated BusyPeriod for dl6 = "+ busyPeriod +"ms, expected: 9ms",(busyPeriod == 9));
    }
}
