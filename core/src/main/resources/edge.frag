#version 130

#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_texture; // 0
uniform float vx_offset = 0.30;
uniform float vx_offset2 = 0.60;
uniform float rt_w; // GeeXLab built-in
uniform float rt_h; // GeeXLab built-in

uniform mat3 G[2] = mat3[]
(
	mat3( 1.0, 2.0, 1.0, 0.0, 0.0, 0.0, -1.0, -2.0, -1.0 ),
	mat3( 1.0, 0.0, -1.0, 2.0, 0.0, -2.0, 1.0, 0.0, -1.0 )
);

uniform mat3 G2[9] = mat3[]
(
	1.0/(2.0*sqrt(2.0)) * mat3( 1.0, sqrt(2.0), 1.0, 0.0, 0.0, 0.0, -1.0, -sqrt(2.0), -1.0 ),
	1.0/(2.0*sqrt(2.0)) * mat3( 1.0, 0.0, -1.0, sqrt(2.0), 0.0, -sqrt(2.0), 1.0, 0.0, -1.0 ),
	1.0/(2.0*sqrt(2.0)) * mat3( 0.0, -1.0, sqrt(2.0), 1.0, 0.0, -1.0, -sqrt(2.0), 1.0, 0.0 ),
	1.0/(2.0*sqrt(2.0)) * mat3( sqrt(2.0), -1.0, 0.0, -1.0, 0.0, 1.0, 0.0, 1.0, -sqrt(2.0) ),
	1.0/2.0 * mat3( 0.0, 1.0, 0.0, -1.0, 0.0, -1.0, 0.0, 1.0, 0.0 ),
	1.0/2.0 * mat3( -1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0 ),
	1.0/6.0 * mat3( 1.0, -2.0, 1.0, -2.0, 4.0, -2.0, 1.0, -2.0, 1.0 ),
	1.0/6.0 * mat3( -2.0, 1.0, -2.0, 1.0, 4.0, 1.0, -2.0, 1.0, -2.0 ),
	1.0/3.0 * mat3( 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 )
);


void main()
{
  vec2 uv = gl_TexCoord[0].xy;
  vec3 tc = vec3(1.0, 0.0, 0.0);

  if (uv.x < (vx_offset-0.005))
  {
    mat3 I;
    float cnv[2];
    vec3 sample;

    // fetch the 3x3 neighbourhood and use the RGB vector's length as intensity value
    for (int i=0; i<3; i++)
    {
      for (int j=0; j<3; j++)
      {
        sample = texelFetch(u_texture, ivec2(gl_FragCoord.xy) + ivec2(i-1,j-1), 0).rgb;
        I[i][j] = length(sample);
      }
    }

    // calculate the convolution values for all the masks
    for (int i=0; i<2; i++)
    {
      float dp3 = dot(G[i][0], I[0]) + dot(G[i][1], I[1]) + dot(G[i][2], I[2]);
      cnv[i] = dp3 * dp3;
    }

    tc = vec3(0.5 * sqrt(cnv[0]*cnv[0]+cnv[1]*cnv[1]));
  }
  else if ((uv.x >= (vx_offset+0.005)) && (uv.x < (vx_offset2-0.005)))
  {
    mat3 I;
    float cnv[9];
    vec3 sample;
    int i, j;

    // fetch the 3x3 neighbourhood and use the RGB vector's length as intensity value
    for (i=0; i<3; i++)
    {
      for (j=0; j<3; j++)
      {
        sample = texelFetch(u_texture, ivec2(gl_FragCoord.xy) + ivec2(i-1,j-1), 0).rgb;
        I[i][j] = length(sample);
      }
    }

    // calculate the convolution values for all the masks
    for (i=0; i<9; i++)
    {
      float dp3 = dot(G2[i][0], I[0]) + dot(G2[i][1], I[1]) + dot(G2[i][2], I[2]);
      cnv[i] = dp3 * dp3;
    }

    //float M = (cnv[0] + cnv[1]) + (cnv[2] + cnv[3]); // Edge detector
    //float S = (cnv[4] + cnv[5]) + (cnv[6] + cnv[7]) + (cnv[8] + M);
    float M = (cnv[4] + cnv[5]) + (cnv[6] + cnv[7]); // Line detector
    float S = (cnv[0] + cnv[1]) + (cnv[2] + cnv[3]) + (cnv[4] + cnv[5]) + (cnv[6] + cnv[7]) + cnv[8];

    tc = vec3(sqrt(M/S));
  }
  else if (uv.x>=(vx_offset2+0.005))
  {
    tc = texture2D(u_texture, uv).rgb;
  }
	gl_FragColor = vec4(tc, 1.0);
}