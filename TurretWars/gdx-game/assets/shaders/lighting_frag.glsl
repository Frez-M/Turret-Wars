precision mediump float;

uniform vec4 u_color;

varying vec4 v_position;
varying vec2 v_texCoord0;

uniform sampler2D u_texture;

void main() {
	vec4 tex = texture2D(u_texture, v_texCoord0);
	gl_FragColor = tex * u_color;
}


