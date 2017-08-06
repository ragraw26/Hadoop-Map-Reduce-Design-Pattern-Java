package part2_nyse_avgPrice;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NYSE_AveragePrice {

    public static class TokenizerMapper
            extends Mapper<LongWritable, Text, Text, DoubleWritable> {

        private static DoubleWritable one = new DoubleWritable(1.0D);
        private Text nyse_symbol = new Text();

        public void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DoubleWritable>.Context context)
                throws IOException, InterruptedException {
            if (!value.toString().contains("exchange")) {
                String[] input = value.toString().split(",");
                this.nyse_symbol .set(input[1]);
                one.set(Double.parseDouble(input[4]));
                context.write(this.nyse_symbol, one);
            }
        }
    }

    public static class IntSumReducer
            extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

        private DoubleWritable result = new DoubleWritable(1.0D);

        public void reduce(Text key, Iterable<DoubleWritable> values, Reducer<Text, DoubleWritable, Text, DoubleWritable>.Context context)
                throws IOException, InterruptedException {
            double sum = 0.0D;
            int n = 0;
            for (DoubleWritable val : values) {
                sum += val.get();
                n++;
            }
            double avg = sum / n;
            this.result.set(avg);
            context.write(key, this.result);
        }
    }

    public static void main(String[] args)
            throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "stock price");
        job.setJarByClass(NYSE_AveragePrice.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
