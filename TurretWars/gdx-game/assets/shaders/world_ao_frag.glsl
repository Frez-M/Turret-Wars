precision mediump float;

uniform vec4 u_color;

varying vec4 v_position;
varying vec2 v_texCoord0;

uniform sampler2D u_texture;

uniform int stage;

const float radius = 5.0;

void main() {
	if (stage == 1) {
		gl_FragColor = vec4(1.0);
	} else if (stage == 2) {
		float tex = 0.0;
		
		for (float x = -radius; x <= radius; x += 1.0) {
			float t = texture2D(u_texture, v_texCoord0 + vec2(x, 0.0)).r;
			float mul = 1.0 - abs(x / radius);
			tex += t * mul;
		}
		
		gl_FragColor = vec4(vec3(1.0), tex);
	} else if (stage == 3) {
		float tex = 0.0;
		
		for (float y = -radius; y <= radius; y += 1.0) {
			float t = texture2D(u_texture, v_texCoord0 + vec2(0.0, y)).r;
			float mul = 1.0 - abs(y / radius);
			tex += t * mul;
		}
		
		gl_FragColor = vec4(vec3(1.0), tex);
	}
}


