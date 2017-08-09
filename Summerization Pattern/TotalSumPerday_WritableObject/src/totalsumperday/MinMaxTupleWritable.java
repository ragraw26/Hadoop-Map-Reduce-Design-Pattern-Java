/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totalsumperday;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.io.Writable;

public class MinMaxTupleWritable implements Writable {

    private float total = 0.00f;
    private float minprice = 0.00f;
    private float maxprice = 0.00f;

    @Override
    public void write(DataOutput d) throws IOException {
        d.writeFloat(total);
        d.writeFloat(minprice);
        d.writeFloat(maxprice);

    }

    @Override
    public void readFields(DataInput di) throws IOException {
        // Read the data out in the order it is written,
        // creating new Date objects from the UNIX timestamp
        total = di.readFloat();
        minprice = di.readFloat();
        maxprice = di.readFloat();
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getMinprice() {
        return minprice;
    }

    public void setMinprice(float minprice) {
        this.minprice = minprice;
    }

    public float getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(float maxprice) {
        this.maxprice = maxprice;
    }

    @Override
    public String toString() {
        return minprice + "\t" + maxprice + "\t" + total;
    }

}
