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
public class TTFrame implements Frame, Comparable<TTFrame>{
    
    /*Constructor*/
    public TTFrame(int id, double c, double offset, double rate, DataPath dp){
        _c=c;
        _offset=offset;
        _dp=dp;
        _id = id;
        _rate=rate;
        _t=offset+c;
    }
    public TTFrame(int id, double c, double offset, double rate){
        _id = id;
        _c=c;
        _offset=offset;
        _rate=rate;
        _t=offset+c;
    }
    /*Attributes*/
    private int _id;
    private double _c; //transimission delay
    private double _offset; //start time
    private double _rate;
    private double _t;
    
    private DataPath _dp = new DataPath();
    
    /*Methods*/
    public void setC(double delay){
        _c=delay;
    }
    public final double getC(){
        return _c;
    }
    public void setOffset(double offs){
        _offset=offs;
    }
    public final double getOffset(){
        return _offset;
    }
    public void setDataPath(DataPath d){
        _dp=d;
    }
    public final DataPath getDataPath(){
        return _dp;
    }
    public final int getID(){
        return _id;
    }
    public final double getRate(){
        return _rate;
    }
    public final double getTj(){
        return _t;
    }
    @Override
    public int compareTo(TTFrame f) {
		double comparedC = f.getC();
		if (this.getC() > comparedC) {
			return 1;
		} else if (this.getC() == comparedC) {
			return 0;
		} else {
			return -1;
		}
    }
}
