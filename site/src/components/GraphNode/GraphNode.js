import React from 'react';
import PropTypes from 'prop-types';
import './GraphNode.css';

class GraphNode extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        const rectid = 'rect' + this.props.id;

        return (
            <g>
                <rect id={rectid} x={this.props.x} y={this.props.y} width={this.props.viewparams.nodewidth}
                      height={this.props.viewparams.nodeheight} stroke="black"
                      fill="transparent"
                      strokeWidth="3"/>
                <text x={this.props.x + this.props.viewparams.textshiftx} y={this.props.y + this.props.viewparams.textshifty}
                      className="small">{this.props.header}</text>

            </g>

            // <div className="GraphNode">
            //     Node {this.props.header}
            // </div>
        );
    }
}


GraphNode.propTypes = {};

GraphNode.defaultProps = {};

export default GraphNode;
