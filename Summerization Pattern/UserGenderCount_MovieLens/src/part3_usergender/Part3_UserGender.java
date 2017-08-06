/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part3_usergender;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author Rajat
 */
public class Part3_UserGender {

    /**
     * @param args the command line arguments
     */
    
     public static class TokenizerMapper
            extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text users = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {

            final String line = value.toString();
            final String[] data = line.split("::");
            if (data.length > 1) {
                final String users_movie = data[1];
                users.set(users_movie);
                context.write(users, one);
            }
        }
    }

    public static class IntSumReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable gender_count = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            gender_count.set(sum);
            context.write(key, gender_count);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // TODO code application logic here        
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "user gender");
        job.setJarByClass(Part3_UserGender.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer .class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    
}
