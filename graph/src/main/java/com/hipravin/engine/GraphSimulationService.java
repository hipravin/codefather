package com.hipravin.engine;

import com.hipravin.api.model.GraphAnimationDto;
import com.hipravin.engine.model.Graph;
import com.hipravin.engine.physics.graph.GraphParticleSystem;
import com.hipravin.engine.physics.graph.GraphPhysicSimulation;
import com.hipravin.engine.physics.graph.GraphPhysicsRules;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

@Service
public class GraphSimulationService {
    //TODO: consider using cache to cleanup old simulations, e.g. Caffeine
    private final ConcurrentMap<String, GraphPhysicSimulation> simulationsInProgress = new ConcurrentHashMap<>();

    private final GraphBuildService graphBuildService;

    //Physic rules to be configured explicitely, maybe in yaml, thus making them a Spring Bean and autowire
    private final GraphPhysicsRules commonGraphRules;

    public GraphSimulationService(GraphBuildService graphBuildService, GraphPhysicsRules commonGraphRules) {
        this.graphBuildService = graphBuildService;
        this.commonGraphRules = commonGraphRules;
    }

    public GraphAnimationDto computeGraphSimulationTick(String graphId, long tick) {
        GraphPhysicSimulation simulation = simulationsInProgress.computeIfAbsent(graphId, buildGraphSimulationById(graphId));

        simulation.advanceToTick(tick);

        return simulation.mapCurrentStateToAnimationDto();
    }

    Function<String, GraphPhysicSimulation> buildGraphSimulationById(String graphId) {
        //using function rather than if(contains) because it will be called atomically per key
        //just playing with code actually...
        return (id) -> {
            Graph graph = graphBuildService.buildOrFindGraph(graphId);
            GraphParticleSystem graphParticleSystem = GraphParticleSystem.initFromGraphWithRandomLocations(graph, commonGraphRules);

            return new GraphPhysicSimulation(graph, graphParticleSystem);
        };
    }


}
