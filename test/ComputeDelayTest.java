/*
 * Copyright (C) 2015 Pierre-Yves
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
import network_frames.Frame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 *
 * @author Pierre-Yves
 * @creationDate    October 13th 2015
 * @lastUpdate      November 9th 2015
 * 
 */
public class ComputeDelayTest {
    //Tests
    @Test
    public final void testComputeDelay_f12(){
        /*
                Data from the first example
        */
        //Nodes
        EndSystem ES1 = new EndSystem("ES1");
        EndSystem ES2 = new EndSystem("ES2");
        EndSystem ES3 = new EndSystem("ES3");
        EndSystem ES4 = new EndSystem("ES4");
        EndSystem ES5 = new EndSystem("ES5");
        NetworkNode NS1 = new NetworkNode("NS1");
        NetworkNode NS2 = new NetworkNode("NS2");        
        
        //Links
            DataLink dl1 = new DataLink(ES1, NS1,1);
            DataLink dl2 = new DataLink(NS1, NS2,2);
            DataLink dl3 = new DataLink(ES5, NS2,3);
            DataLink dl4 = new DataLink(ES2, NS1,4);
            DataLink dl5 = new DataLink(ES3, NS2,5);
            DataLink dl6 = new DataLink(ES4, NS2,6);
        
        //Schedule of TT frames
        TTFrame tt1 = new TTFrame(1,3,3,32);
        dl1.getTT_schedule().add(tt1);
        TTFrame tt2 = new TTFrame(2,2,9,32);
        dl1.getTT_schedule().add(tt2);
        
        TTFrame tt3 = new TTFrame(1,3,7,32);
        dl2.getTT_schedule().add(tt3);
        TTFrame tt4 = new TTFrame(3,3.5,12.5,32);
        dl2.getTT_schedule().add(tt4);
        
        TTFrame tt5 = new TTFrame(1,3,11,32);
        dl6.getTT_schedule().add(tt5);
        
        //The schedule of RC frames is empty at the beginning.
        
        //DataPath of frame 13
        DataPath dp13 = new DataPath();
        dp13.getDataLinksQueue().add(dl2);
        //dp13.getDataLinksQueue().add(dl6);
        
        //Instance of f13 on dl2, starts at 13
        RCFrame f13 = new RCFrame(130,2,13,32,dp13);
        //TODO: Add the frame to the dataLink, Danger of infinite loop.
        dl2.getRC_schedule().add(f13);
        
        //DataPath of frame 13_1
        //The DataPath is only an indication on which datalinks are concerned by the frame.
        DataPath dp13_1 = new DataPath();
        dp13_1.getDataLinksQueue().add(dl6);
        //dp13.getDataLinksQueue().add(dl6);
        
        //Instance of f13 on dl6, starts at 23.5
        RCFrame f13_1 = new RCFrame(131,2,23.5,32,dp13_1);
        //TODO: Add the frame to the dataLink, Danger of infinite loop.
        dl6.getRC_schedule().add(f13_1);
        
        //DataPath of frame 10
        DataPath dp14 = new DataPath();
        dp14.getDataLinksQueue().add(dl2);
        
        //Frame 14, start at 13ms
        RCFrame f14 = new RCFrame(14,1.5,15,32,dp14);
        //TODO: Add the frame to the dataLink, Danger of infinite loop.
        dl2.getRC_schedule().add(f14);
        
        
        //DataPath of frame 10
        DataPath dp10 = new DataPath();
        dp10.getDataLinksQueue().add(dl1);
        dp10.getDataLinksQueue().add(dl2);
        
        //Frame 10, start at 1ms
        RCFrame f10 = new RCFrame(10,1.5,1,32, dp10);
        //TODO: Add the frame to the dataLink, Danger of infinite loop.
        dl1.getRC_schedule().add(f10);
        dl2.getRC_schedule().add(f10);
        
        //DataPath of frame 11
        DataPath dp11 = new DataPath();
        dp11.getDataLinksQueue().add(dl1);
        dp11.getDataLinksQueue().add(dl2);
        
        //Frame 10, start at 1ms
        RCFrame f11 = new RCFrame(11,2.5,1,32,dp11);
        //TODO: Add the frame to the dataLink, Danger of infinite loop.
        dl1.getRC_schedule().add(f11);
        dl2.getRC_schedule().add(f11);
        
        //DataPath of frame 12
        DataPath dp12 = new DataPath();
        dp12.getDataLinksQueue().add(dl1);
        dp12.getDataLinksQueue().add(dl2);
        dp12.getDataLinksQueue().add(dl6);
        
        //Frame 12, start at 1ms
        RCFrame f12 = new RCFrame(12,2,1,32,dp12);
        //TODO: Add the frame to the dataLink, Danger of infinite loop.
        dl1.getRC_schedule().add(f12);
        dl2.getRC_schedule().add(f12);
        dl6.getRC_schedule().add(f12);
        
        //RC queue
        LinkedList<RCFrame> RCFrameQueue = new LinkedList<RCFrame>();
        RCFrameQueue.add(f13);
        RCFrameQueue.add(f13_1);
        RCFrameQueue.add(f14);
        RCFrameQueue.add(f10);
        RCFrameQueue.add(f11);
        RCFrameQueue.add(f12);
        
        //Computation
        double delay = f12.computeDelay(RCFrameQueue);
        

        assertTrue("Wrong result, WCD calculated = "+ delay +"ms, expected: 26.5ms",(delay == 26.5));
    }
}
