/**
 *  This file contains the variables used in other gulp files
 *  which defines tasks
 *  By design, we only put there very generic config values
 *  which are used in several places to keep good readability
 *  of the tasks
 */

var gutil = require('gulp-util');
var notifier = require('node-notifier');
var path = require('path');

/**
 *  The main paths of your project handle these with care
 */
exports.paths = require('../.yo-rc')['generator-gulp-angular'].props.paths;

/**
 *  Wiredep is the lib which inject bower dependencies in your project
 *  Mainly used to inject script tags in the index.html but also used
 *  to inject css preprocessor deps and js files in karma
 */
exports.wiredep = {
  exclude: [/jquery/],
  directory: 'bower_components'
};

/**
 *  Common implementation for an error handler of a Gulp plugin
 */
exports.errorHandler = function(title) {
  'use strict';

  return function(err) {
    gutil.log(gutil.colors.red('[' + title + ']'), err.toString());
    notifier.notify({
    	title: title,
    	message: "Got a " + title + " error. See the terminal!",
    	sound: true,
    	icon: path.join(__dirname, 'gulp.png')
    });
    this.emit('end');
  };
};
