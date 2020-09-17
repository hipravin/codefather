import React from 'react';
import PropTypes from 'prop-types';
import './GraphLink.css';

class GraphLink extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <line x1={this.props.x1} y1={this.props.y1} x2={this.props.x2} y2={this.props.y2} stroke="black" strokeWidth="2"/>
        )
    }
}

GraphLink.propTypes = {};

GraphLink.defaultProps = {};

export default GraphLink;
