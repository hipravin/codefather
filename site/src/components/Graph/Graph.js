import React from 'react';
import PropTypes from 'prop-types';
import './Graph.css';
import GraphNode from "../GraphNode/GraphNode";
import GraphLink from "../GraphLink/GraphLink";

class Graph extends React.Component {
    viewparams = {
        maxwidth: 1000,
        maxheight: 800,
        textshiftx: 5,
        textshifty: 20,
        nodewidth: 150,
        nodeheight: 30
    };


    constructor(props) {
        super(props);
    }

    render() {
        const id2node = new Map(this.props.graph.nodes.map(node => [node.id, node]));

        const links = this.props.graph.links.map(link => {
                const fromNcp = this.nodeScreenCenterPosition(id2node.get(link.fromNodeId));
                const toNcp = this.nodeScreenCenterPosition(id2node.get(link.toNodeId));

                return <GraphLink x1={fromNcp.x} y1={fromNcp.y}
                                  x2={toNcp.x} y2={toNcp.y} key={link.fromNodeId + '_' + link.toNodeId}/>;
            }
        );
        const nodes = this.props.graph.nodes.map(node => {
                const np = this.nodeScreenPosition(node);

                return <GraphNode header={node.text} key={node.id} id={node.id} x={np.x} y={np.y}
                                  viewparams={this.viewparams}/>;
            }
        );

        return (
            <g>
                {links}
                {nodes}
            </g>
        );
    }

    nodeScreenPosition(node) {
        const x = this.viewparams.maxwidth * node.position.x;
        const y = this.viewparams.maxheight * node.position.y;

        return {"x": x, "y": y};
    }

    nodeScreenCenterPosition(node) {
        const x = this.viewparams.maxwidth * node.position.x;
        const y = this.viewparams.maxheight * node.position.y;

        const xcenter = this.viewparams.nodewidth / 2 + x;
        const ycenter = this.viewparams.nodeheight / 2 + y;

        return {"x": xcenter, "y": ycenter};
    }
}

Graph.propTypes = {};

Graph.defaultProps = {};

export default Graph;
