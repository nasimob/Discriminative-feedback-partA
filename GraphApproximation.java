public class GraphApproximation {

    public static int[] approximate(int[] values) {
        int n = values.length;

        // Calculate 1/yi values
        double[] oneOverYi = new double[n];
        for (int i = 0; i < n; i++) {
            oneOverYi[i] = 1.0 / values[i];
        }

        // Calculate the sums required for the equations
        double nd = (double)n;
        double sumXiOverYi = 0;
        double sumXiSquared = nd*(nd+1)*(2*nd+1)/6;
        double sumXi = nd*(nd+1)/2;
        double sumOneOverYi = 0;
        for (int i = 0; i < n; i++) {
            if(values[i] == 0)
              values[i] = Integer.MAX_VALUE;
            sumXiOverYi += (double)i / values[i];
            sumOneOverYi += 1.0 / values[i];
        }

        double a = ((n * sumXiOverYi) - (sumXi * sumOneOverYi)) / ((n * sumXiSquared) - (sumXi * sumXi));
        double b = (sumXiSquared * sumOneOverYi - sumXi * sumXiOverYi)/((n * sumXiSquared) - (sumXi * sumXi));

        System.out.println("Optimal values:");
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        int[] approx = new int[n];
        for(int i = 0; i < n; i++) {
          approx[i] = (int)(1/(a*i + b));
        }

        return approx;
    }
}
