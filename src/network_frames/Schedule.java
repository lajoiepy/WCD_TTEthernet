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
import java.util.*;
/**
 *
 * @author Pierre-Yves Lajoie
 * @creationDate    October 7th 2015
 * @lastUpdate      October 13th 2015
 * 
 */
public class Schedule {
    /*Constructor*/
    
    /*Attributes*/
    /*private class ComparatorFrame implements Comparator<Frame>{
        @Override
        public int compare(Frame f1, Frame f2){
            if(f1.getOffset()<f2.getOffset())
                return -1;
            else if(f1.getOffset()>f2.getOffset())
                return 1;
            else
                return 0;
        }
    }
    ComparatorFrame cmp;
    PriorityQueue<Frame> framesQueue = new PriorityQueue<Frame>(3,cmp);
    */
    LinkedList<Frame> framesList = new LinkedList<Frame>();
    /*Methods*/
    public LinkedList<Frame> getFramesList(){
        return framesList;
    }
}
