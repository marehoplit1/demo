package com.example.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class Dijkstra {

    private HashMap<String, Vert> airports;

    public Dijkstra() {
    }


    public void readAirportsFromFile() throws IOException {

        String f= "airports.txt";
        airports = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/".concat(f)));
        String s;
        while ((s = br.readLine()) != null) {
            String[] data = s.split(",");
            Vert airport = new Vert(data[0]);

            airports.put(data[0], airport);
        }

        br.close();
        initializeEdges("routes.txt");
    }

    public void initializeEdges(String f) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/".concat(f)));
        String s;
        int err = 0;
        int cnt = 0;
        while ((s = br.readLine()) != null) {
            String[] data = s.split(",");
            cnt++;
            try {
                Vert source = airports.get(data[3]);

                Edge route = new Edge((Double.parseDouble(data[9])), airports.get(data[3]), airports.get(data[5]));

                if (source != null && airports.get(data[5]) != null)
                    source.addNeighbour(route);
                else {
                    err++;
                }

            } catch (NumberFormatException e) {
                System.out.println("PROBLEM");
            }
        }

        br.close();
    }

    public ResultDto getShortestPathFromSourceTarget(String s, String t, Dijkstra dijkstra) {
        PathFinder shortestPath = new PathFinder();
    //    dijkstra.resetNodes();
        shortestPath.ShortestP(dijkstra.airports.get(s));
        System.out.println("Minimum distance from A to B: " + dijkstra.airports.get(t).getDist());
        System.out.println("Shortest Path from A to B: " + shortestPath.getShortestP(dijkstra.airports.get(t)));
        ResultDto resultDto = new ResultDto();
        resultDto.setPrice( dijkstra.airports.get(t).getDist());
        resultDto.setRoute(shortestPath.getShortestP(dijkstra.airports.get(t)));
        return resultDto;
    }

    private void resetNodes() {
        Map<String, Vert>  map = airports;
        for (Map.Entry<String, Vert> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            entry.getValue().setVisited(false);
        }
    }
}
