package iris;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        App a = new App();
        a.test();
    }

    public void test() throws IOException {

        /*List<Iris> irisList = null; //load data from file iris.data
        IrisDataSetHelper helper = new IrisDataSetHelper(irisList);

        //get average sepal width
        Double avgSetalLength = null;
        System.out.println(avgSetalLength);

        //get average petal square - petal width multiplied on petal length
        Double avgPetalLength = null;
        System.out.println(avgPetalLength);

        //get average petal square for flowers with sepal width > 4
        Double avgPetalSquare = null;
        System.out.println(avgPetalSquare);

        //get flowers grouped by Petal size (Petal.SMALL, etc.)
        Map groupsByPetalSize = null;
        System.out.println(groupsByPetalSize);

        //get max sepal width for flowers grouped by species
        Map maxSepalWidthForGroupsBySpecies = null;
        System.out.println(maxSepalWidthForGroupsBySpecies);*/

        List<Iris> iris_list = Files.lines(Paths.get("iris.data")).map(Iris::parse).collect(Collectors.toList());
        IrisDataSetHelper iris_helper = new IrisDataSetHelper(iris_list);

        Double avgSetalLength = iris_helper.getAverage(data -> data.getSepalWidth());
        Double avgPetalLength = iris_helper.getAverage(data -> data.getPetalWidth() * data.getPetalLength());
        Double avgPetalSquare = iris_helper.getAverageWithFilter(data -> data.getSepalWidth() > 4, data -> data.getPetalWidth() * data.getPetalLength());
        Map groupsByPetalSize = iris_helper.groupBy(iris -> Iris.classifyByPatel((Iris)iris));
        Map maxSepalWidthForGroupsBySpecies = iris_helper.maxFromGroupedBy(iris -> Iris.classifyByPatel((Iris)iris), data -> data.getSepalWidth());

        System.out.printf("avgSetalLength: %s\navgPetalLength: %s\navgPetalSquare: %s\ngroupsByPetalSize: %s\nmaxSepalWidthForGroupsBySpecies: %s",
                avgSetalLength,
                avgPetalLength,
                avgPetalSquare,
                groupsByPetalSize,
                maxSepalWidthForGroupsBySpecies
        );
    }

}

