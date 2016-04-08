'use strict';
import React, {
  requireNativeComponent, PropTypes, Image
} from 'react-native';
import UIManager from 'UIManager';

class ImageExtendView extends React.Component {
  static propTypes = {
    ...Image.propTypes
  }
  render() {
    return (<Image {...this.props} />);
  }
  getPixel(x, y) {
    UIManager.dispatchViewManagerCommand(
      React.findNodeHandle(this),
      UIManager.ImageExtendView.Commands.getPixleAt,
      [x, y]
    );
  }
}

export default requireNativeComponent('ImageExtendView', ImageExtendView);
