package com.hipravin.engine;

import com.hipravin.api.model.GraphAnimationDto;
import com.hipravin.engine.model.Graph;
import com.hipravin.engine.physics.graph.GraphParticleSystem;
import com.hipravin.engine.physics.graph.GraphPhysicSimulation;
import com.hipravin.engine.physics.graph.GraphPhysicsRules;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GraphSimulationService {

//    private final ConcurrentMap<String, GraphPhysicSimulation> simulationsInProgress = new ConcurrentHashMap<>();

    private final GraphBuildService graphBuildService;

    //Physic rules to be configured explicitely, maybe in yaml, thus making them a Spring Bean and autowire
    private final GraphPhysicsRules commonGraphRules;

    public GraphSimulationService(GraphBuildService graphBuildService, GraphPhysicsRules commonGraphRules) {
        this.graphBuildService = graphBuildService;
        this.commonGraphRules = commonGraphRules;
    }

    public GraphAnimationDto buildIfRequiredAndAdvance(Map<String, GraphPhysicSimulation> simulationsInProgress, String graphId, long tick) {
        if(tick == 0) {
            simulationsInProgress.remove(graphId);
        }
        simulationsInProgress.computeIfAbsent(graphId, this::buildSimulationById);
        return advanceSimulationToTick(simulationsInProgress.get(graphId), tick);
    }


    public GraphPhysicSimulation buildSimulationById(String graphId) {
        Graph graph = graphBuildService.buildOrFindGraph(graphId);
        GraphParticleSystem graphParticleSystem = GraphParticleSystem.initFromGraphWithRandomLocations(graph, commonGraphRules);

        return new GraphPhysicSimulation(graph, graphParticleSystem);
    }

    public GraphAnimationDto advanceSimulationToTick(GraphPhysicSimulation simulation, long tick) {
        simulation.advanceToTick(tick);
        return simulation.mapCurrentStateToAnimationDto();
    }
}
