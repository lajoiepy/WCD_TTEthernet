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
package network_components;
import network_frames.*;
/**
 *
 * @author Pierre-Yves Lajoie
 * @creationDate    October 7th 2015
 * @lastUpdate      October 13th 2015
 * 
 */
public class DataLink {
        
    /*Constructor*/
    public DataLink(NetworkNode node1, NetworkNode node2, int id){
        _node1 = node1;
        _node2 = node2;
        _id=id;
    }
    
    /*Attributes*/
    private int _id;
    private NetworkNode _node1;
    private NetworkNode _node2;
    private Schedule _RC_schedule = new Schedule();
    private Schedule _TT_schedule = new Schedule();
    /*Methods*/
    public final int getID(){
        return _id;
    }
    public final Schedule getRC_schedule(){
        return _RC_schedule;
    }
    public final Schedule getTT_schedule(){
        return _TT_schedule;
    }
    public boolean compare(DataLink dl){
        return dl._node1==_node1 && dl._node2==_node2;
    }
}
