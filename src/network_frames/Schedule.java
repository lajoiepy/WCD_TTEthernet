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
 * @lastUpdate      October 22th 2015
 * 
 */
public class Schedule {
    /*Constructor*/
    
    /*Attributes*/
    private LinkedList<Frame> _framesList = new LinkedList<Frame>();
    private LinkedList<Frame> _sortedList = new LinkedList<Frame>();
    /*Methods*/
    public LinkedList<Frame> getFramesList(){
        return _framesList;
    }
    public LinkedList<Frame> getSortedList(){
        return _sortedList;
    }
    
    public void add(Frame frame){
        _framesList.add(frame);
        _sortedList.add(frame);
        Collections.sort(_sortedList, new Comparator<Frame>() {
         @Override
         public int compare(Frame o1, Frame o2) {
             if (o1.getC()>o2.getC())
                return -1;
             else if (o1.getC()<o2.getC())
                return 1;
             else
                return 0;
         }
        });
    }
}
