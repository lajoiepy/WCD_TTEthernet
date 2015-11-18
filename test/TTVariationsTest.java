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
 * @lastUpdate      November 17th 2015
 */
public class TTVariationsTest {
    //Tests
    @Test
    public final void testComputeBusyPeriod_DL1_f12_TT1(){
        //Node of dl1
        EndSystem ES1 = new EndSystem("ES1");
        NetworkNode NS1 = new NetworkNode("NS1");
        //DataLink and DataPath
        DataLink dl1 = new DataLink(ES1, NS1,1);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //TT1 moved to 2ms, so the f10 goes between TT1 and TT2.
        TTFrame tt1 = new TTFrame(1,3,2,32);
        dl1.getTT_schedule().getFramesList().add(tt1);
        TTFrame tt2 = new TTFrame(2,2.5,9,32);
        dl1.getTT_schedule().getFramesList().add(tt2);
         //Schedule of RC frames
        RCFrame rc1 = new RCFrame(10,1.5,1,32);
        dl1.getRC_schedule().getFramesList().add(rc1);
        RCFrame rc2 = new RCFrame(11,2.5,1,32);
        dl1.getRC_schedule().getFramesList().add(rc2);
        //Frame 12
        dp12.getDataLinksQueue().add(dl1);
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        dl1.getRC_schedule().getFramesList().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl1);
        assertTrue("Wrong result, calculated BusyPeriod for dl1 = "+ busyPeriod +"ms, expected: 15ms",(busyPeriod == 15));
    }
    
    @Test
    public final void testComputeBusyPeriod_DL2_f12_TT3(){
        //Node of dl2
        NetworkNode NS1 = new NetworkNode("NS1");
        NetworkNode NS2 = new NetworkNode("NS2"); 
        //DataLink and DataPath
        DataLink dl2 = new DataLink(NS1, NS2,2);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        TTFrame tt2 = new TTFrame(1,3,7,32);
        dl2.getTT_schedule().getFramesList().add(tt2);
        //TT3 moved to 15
        TTFrame tt3 = new TTFrame(3,3.5,12.5,32);
        dl2.getTT_schedule().getFramesList().add(tt3);
         //Schedule of RC frames
        RCFrame rc3 = new RCFrame(13,2,16,32);
        dl2.getRC_schedule().getFramesList().add(rc3);
        RCFrame rc4 = new RCFrame(14,1.5,18,32);
        dl2.getRC_schedule().getFramesList().add(rc4);
        RCFrame rc5 = new RCFrame(10,1.5,19.5,32);
        dl2.getRC_schedule().getFramesList().add(rc5);
        RCFrame rc6 = new RCFrame(11,2.5,21,32);
        dl2.getRC_schedule().getFramesList().add(rc6);
        //Frame 12
        dp12.getDataLinksQueue().add(dl2);
        RCFrame f12 = new RCFrame(12,2,17.5,32,dp12);
        dl2.getRC_schedule().getFramesList().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl2);
        assertTrue("Wrong result, calculated BusyPeriod for dl2 = "+ busyPeriod +"ms, expected: 10.5ms",(busyPeriod == 10.5));
    }
    @Test
    public final void testComputeBusyPeriod_DL6_f12_TT1(){
        //Node of dl6
        EndSystem ES4 = new EndSystem("ES4");
        NetworkNode NS2 = new NetworkNode("NS2");
        //DataLink and DataPath
        DataLink dl6 = new DataLink(ES4, NS2,6);
        DataPath dp12 = new DataPath();
        
        //Schedule of TT frames
        //TT1 moved to 22ms, so the frames are push back later
        TTFrame tt1 = new TTFrame(1,3,11,32);
        dl6.getTT_schedule().getFramesList().add(tt1);
         //Schedule of RC frames
        RCFrame rc7 = new RCFrame(13,2,23.5,32);
        dl6.getRC_schedule().getFramesList().add(rc7);
        //Frame 12
        dp12.getDataLinksQueue().add(dl6);
        RCFrame f12 = new RCFrame(12,2,25.5,32,dp12);
        dl6.getRC_schedule().getFramesList().add(f12);
        double busyPeriod = f12.computeBusyPeriod(dl6);
        assertTrue("Wrong result, calculated BusyPeriod for dl6 = "+ busyPeriod +"ms, expected: 3.5ms",(busyPeriod == 3.5));
    }
}