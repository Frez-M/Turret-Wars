precision mediump float;

uniform vec4 u_color;

varying vec4 v_position;
varying vec2 v_texCoord0;

uniform sampler2D u_texture;

uniform int stage;
uniform vec2 imgSize;

const float radius = 5.0;
const float quality = 4.0;

void main() {
	if (stage == 1) {
		gl_FragColor = vec4(1.0);
	} else if (stage == 2) {
		float tex = 0.0;
		
		for (float x = -radius; x <= radius; x += 1.0) {
			tex += texture2D(u_texture, v_texCoord0 + vec2(x / (imgSize.x / quality), 0.0)).a;
		}
		tex /= radius * 2.0;
		gl_FragColor = vec4(vec3(0.0), tex);
	} else if (stage == 3) {
		float tex = 0.0;
		
		for (float y = -radius; y <= radius; y += 1.0) {
			tex += texture2D(u_texture, v_texCoord0 + vec2(0.0, y / (imgSize.y / quality))).a;
		}
		tex /= radius * 2.0;
		gl_FragColor = vec4(vec3(0.0), tex);
	} else {
		gl_FragColor = vec4(1.0, 0.0, 0.0, 0.5);
	}
}


