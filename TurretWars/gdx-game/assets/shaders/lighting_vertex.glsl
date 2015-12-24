precision mediump float;

uniform mat4 u_projTrans;

attribute vec4 a_position;
attribute vec2 a_texCoord0;

varying vec4 v_position;
varying vec2 v_texCoord0;

void main() {
	v_position = a_position;
	v_texCoord0 = a_texCoord0;
	
	gl_Position = u_projTrans * a_position;
	
}
