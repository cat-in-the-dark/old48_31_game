#version 130


varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

//varying vec2 vTexCoord;
uniform vec2 resolution;
//uniform sampler2D uImage0;

uniform float scale = 2.125;

void main()
{
    if (mod(floor(v_texCoords.y * resolution.y / scale), 2.0) == 0)
        gl_FragColor = texture2D(u_texture, v_texCoords)*0.6 + vec4(0.2, 0.2, 0.2, 0.4);
    else
        gl_FragColor = texture2D(u_texture, v_texCoords);
}