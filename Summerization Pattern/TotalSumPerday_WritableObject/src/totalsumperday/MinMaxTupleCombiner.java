/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totalsumperday;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MinMaxTupleCombiner extends Reducer<Text, MinMaxTupleWritable, Text, MinMaxTupleWritable> {

    // Our output value Writable
    private MinMaxTupleWritable result = new MinMaxTupleWritable();

    @Override
    protected void reduce(Text key, Iterable<MinMaxTupleWritable> values, Context context) throws IOException, InterruptedException {

        // Initialize our result
        result.setMinprice(0);
        result.setTotal(0);
        result.setMaxprice(0);
        int sum = 0;

        // Iterate through all input values for this key
        for (MinMaxTupleWritable val : values) {
            // If the value's min is less than the result's min
            // Set the result's min to value's
            if (result.getMinprice() == 0 || val.getMinprice() < (result.getMinprice())) {
                result.setMinprice(val.getMinprice());
            }
            // If the value's max is more than the result's max
            // Set the result's max to value's
            if (result.getMaxprice() == 0
                    || val.getMaxprice() > (result.getMaxprice())) {
                result.setMaxprice(val.getMaxprice());
            }

            sum += val.getTotal();
            result.setTotal(sum);

        }

        context.write(key, result);
    }

} 