package com.hipravin;

import com.hipravin.engine.physics.graph.GraphPhysicsInteractionBuilder;
import com.hipravin.engine.physics.graph.GraphPhysicsRules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphPhysicsConfig {

    @Bean
    GraphPhysicsRules commonGraphRules() {
        GraphPhysicsInteractionBuilder physicsInteractionBuilder = new GraphPhysicsInteractionBuilder()
                .withEdgeRepulsion()
                .withPairwiseSquareRepulsion();

        return physicsInteractionBuilder.build();
    }
}
