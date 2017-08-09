/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top25products;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SecondReducer extends Reducer<DoubleWritable, Text, Text, DoubleWritable>{
     
    private static DoubleWritable avgR = new DoubleWritable(1);
    
     
    public void reduce(DoubleWritable avgRating, Text product, Context context) throws IOException, InterruptedException{
        avgR.set(Double.parseDouble(avgRating.toString()));
        context.write(product, avgR);
    }
     
}
