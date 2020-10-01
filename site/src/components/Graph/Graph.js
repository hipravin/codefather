import React from 'react';
import PropTypes from 'prop-types';
import './Graph.css';
import GraphNode from "../GraphNode/GraphNode";
import GraphLink from "../GraphLink/GraphLink";

class Graph extends React.Component {
    viewparams = {
        maxwidth: 1600,
        maxheight: 900,
        textshiftx: 5,
        textshifty: 20,
        nodewidth: 150,
        nodeheight: 30
    };

    xshift = 0;

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        // setInterval(() => {
        //     this.xshift++;
        //     this.forceUpdate();
        // }, 50);
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
                const np = this.nodeLeftTopCornerPosition(node);

                return <GraphNode header={node.text} key={node.id} id={node.id} x={np.x + this.xshift} y={np.y}
                                  viewparams={this.viewparams} scale={node.weight}/>;
            }
        );

        return (
            <g>
                {links}
                {nodes}
            </g>
        );
    }

    nodeLeftTopCornerPosition(node) {
        const x = this.viewparams.maxwidth * node.position.x - this.viewparams.nodewidth / 2;
        const y = this.viewparams.maxheight * node.position.y - this.viewparams.nodeheight / 2;

        return {"x": x, "y": y};
    }

    nodeScreenCenterPosition(node) {
        const x = this.viewparams.maxwidth * node.position.x;
        const y = this.viewparams.maxheight * node.position.y;

        return {"x": x, "y": y};
    }
}

Graph.propTypes = {};

Graph.defaultProps = {};

export default Graph;
