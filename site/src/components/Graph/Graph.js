import React from 'react';
import PropTypes from 'prop-types';
import './Graph.css';
import GraphNode from "../GraphNode/GraphNode";

class Graph extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const nodes = this.props.graph.nodes.map(node =>
            <GraphNode header={node.text} key={node.id}/>
        );

        return (
            <div className="Graph">
                Graph Component.
                {nodes}
            </div>
        );
    }
}

Graph.propTypes = {};

Graph.defaultProps = {};

export default Graph;
