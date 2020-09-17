import React from 'react';
import PropTypes from 'prop-types';
import './GraphNode.css';

class GraphNode extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="GraphNode">
                Node {this.props.header}
            </div>
        );
    }
}


GraphNode.propTypes = {};

GraphNode.defaultProps = {};

export default GraphNode;
