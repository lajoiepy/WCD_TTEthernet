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
/**
 *
 * @author Pierre-Yves Lajoie
 * @creationDate    October 7th 2015
 * @lastUpdate      October 7th 2015
 * 
 */
public class NetworkNode {
        
    /*Constructor*/
    public NetworkNode(String ID){
        id = ID;
    }
    
    /*Attributes*/
    private String id;
   
    /*Methods*/
    public String getID(){
        return id;
    }
}
