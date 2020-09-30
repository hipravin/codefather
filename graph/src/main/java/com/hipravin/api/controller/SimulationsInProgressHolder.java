package com.hipravin.api.controller;

import com.hipravin.engine.physics.graph.GraphPhysicSimulation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * In older approaches we could store it in session, but now since we are approaching stateless infrastructures
 * we need to figure out other options...
 */
@Component
public class SimulationsInProgressHolder {
    private ConcurrentMap<String, GraphPhysicSimulation> simulationsInProgress = new ConcurrentHashMap<>();

    public Map<String, GraphPhysicSimulation> getSimulationsInProgress() {
        return simulationsInProgress;
    }
}
