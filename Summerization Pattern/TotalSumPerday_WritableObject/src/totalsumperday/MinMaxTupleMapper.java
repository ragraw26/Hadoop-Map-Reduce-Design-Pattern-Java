/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totalsumperday;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MinMaxTupleMapper extends Mapper<Object, Text, Text, MinMaxTupleWritable> {

    // Our output key and value Writables
    private Text date = new Text();
    private float price;
    private MinMaxTupleWritable outTuple = new MinMaxTupleWritable();

    // This object will format the creation date string into a Date object
    private final static SimpleDateFormat frmt
            = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        if (!value.toString().contains(" ")) {
            String[] fields = value.toString().split(",");
            String travel_date = fields[2];
            float price = Float.parseFloat(fields[19]);
            if (!fields[19].isEmpty()) {
                this.price = Float.parseFloat(fields[19]);

            } else {
                this.price = 0;
            }
            String o_date = frmt.format(travel_date);
            outTuple.setMinprice(this.price);
            outTuple.setMaxprice(this.price);
            outTuple.setTotal(this.price);
            date.set(o_date);
            context.write(date, outTuple);

        }

    }

}
