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
    public DataLink(NetworkNode Node1, NetworkNode Node2, int ID){
        node1 = Node1;
        node2 = Node2;
        id=ID;
    }
    
    /*Attributes*/
    private int id;
    private NetworkNode node1;
    private NetworkNode node2;
    private Schedule RC_schedule = new Schedule();
    private Schedule TT_schedule = new Schedule();
    /*Methods*/
    public final int getID(){
        return id;
    }
    public final Schedule getRC_schedule(){
        return RC_schedule;
    }
    public final Schedule getTT_schedule(){
        return TT_schedule;
    }
    public boolean compare(DataLink dl){
        return dl.node1==node1 && dl.node2==node2;
    }
}
