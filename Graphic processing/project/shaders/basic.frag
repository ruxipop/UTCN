#version 410 core

in vec3 fPosition;
in vec3 fNormal;
in vec2 fTexCoords;
in vec4 fragPosLightSpace;



out vec4 fColor;

//matrices
uniform mat4 model;
uniform mat4 view;
uniform mat3 normalMatrix;
//lighting
uniform vec3 lightDir;
uniform vec3 lightColor;
uniform vec3 lightColorPos;
//spotlight
struct Spotlight {
    mat4 view;
    mat3 normalM;

    vec3 position;
    vec3 direction;
    float cutOff;
    float outerCutOff;
  
    float constant;
    float linear;
    float quadratic;
  
    vec3 color;
};
#define NR_SPOT_LIGHTS 2
uniform Spotlight spotlight[NR_SPOT_LIGHTS];
// textures
uniform sampler2D diffuseTexture;
uniform sampler2D specularTexture;
uniform sampler2D shadowMap;

//fog
uniform float fogDensity;
float shadow;
//components
vec3 ambient;
float ambientStrength = 0.2f;
vec3 diffuse;
vec3 specular;
float specularStrength = 0.5f;
// pointLight
float constant = 1.0f;
float linear = 0.22f;
float quadratic = 0.20f;

uniform vec3 lightPosEye;


float computeShadow(){

    vec3 normalizedCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
   
    normalizedCoords = normalizedCoords * 0.5 + 0.5;
    
    // Get closest depth value from light's perspective
    float closestDepth = texture(shadowMap, normalizedCoords.xy).r;
    // Get depth of current fragment from light's perspective
    float currentDepth = normalizedCoords.z;
   
    vec3 normal = normalize(normalMatrix * fNormal);
    // Check whether current frag pos is in shadow
    float bias = max(0.05f * (1.0f - dot(normal, lightDir)), 0.005f);
    float shadow = currentDepth - bias > closestDepth ? 1.0f : 0.0f;
    return shadow;
}
vec3 computeDirLight()
{
    //compute eye space coordinates
    vec4 fPosEye = view * model * vec4(fPosition, 1.0f);
    vec3 normalEye = normalize(normalMatrix * fNormal);

    //normalize light direction
   // vec3 lightDirN = vec3(normalize(view * vec4(lightDir, 0.0f)));
   vec3 lightDirN = normalize(lightDir);
    //compute view direction (in eye coordinates, the viewer is situated at the origin
    vec3 viewDir = normalize(- fPosEye.xyz);

    //compute ambient light
    ambient = ambientStrength * lightColor;

    //compute diffuse light
    diffuse = max(dot(normalEye, lightDirN), 0.0f) * lightColor;

    //compute specular light
    vec3 reflectDir = reflect(-lightDirN, normalEye);
    float specCoeff = pow(max(dot(viewDir, reflectDir), 0.0f), 32);
    specular = specularStrength * specCoeff * lightColor;
    
    float shadow = computeShadow();
 return   min((ambient + (1.0f - shadow) * diffuse) * texture(diffuseTexture, fTexCoords).rgb + (1.0f - shadow) * specular * texture(specularTexture, fTexCoords).rgb, 1.0f);

}

vec3 computePointLight(){

   //compute eye space coordinates
   vec4 fPosEye = vec4(fPosition, 1.0f);
    
   vec3 normalEye = normalize(fNormal);

   //compute light direction
   vec3 lightDirN = normalize(lightPosEye - fPosEye.xyz);

   //compute view direction (in eye coordinates, the viewer is situated at the origin
   vec3 viewDirN = normalize(- fPosEye.xyz);
  
  //compute half vector
   vec3 halfVector = normalize(lightDirN + viewDirN);

   //compute distance to light
   float dist = length(lightPosEye - fPosEye.xyz);

   //compute attenuation
   float att = 1.0f / (constant + linear * dist + quadratic * (dist * dist));
   
   //compute ambient light
   vec3 ambient = att * ambientStrength * lightColorPos;
   
   //compute diffuse light
   vec3 diffuse = att * max(dot(normalEye, lightDirN), 0.0f) * lightColorPos;
   
   //compute specular light
   float specCoeff = pow(max(dot(normalEye, halfVector), 0.0f), 32.0f);
   vec3 specular = att * specularStrength * specCoeff * lightColorPos;

   return min((ambient + diffuse) * texture(diffuseTexture, fTexCoords).rgb + specular * texture(specularTexture, fTexCoords).rgb, 1.0f);

}

vec3 computeSpotLight(Spotlight light)
{
    vec4 fPosEye = light.view * model * vec4(fPosition, 1.0f);
    vec3 normalEye = normalize(light.normalM * fNormal);
    vec3 lightPosEye = vec3(light.view * vec4(light.position, 1.0f));
    vec3 viewDir = normalize(-fPosEye.xyz);

    vec3 lightDirN = normalize(lightPosEye - vec3(fPosEye));

    // diffuse shading
    float diff = max(dot(normalEye, lightDirN), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDirN, normalEye);
    float specCoeff = pow(max(dot(viewDir, reflectDir), 0.0f), 32);
    // attenuation
    float distToLight = length(lightPosEye - vec3(fPosEye));
    float attenuation = 1.0f / (light.constant + light.linear * distToLight + light.quadratic * (distToLight * distToLight));
    // spotlight intensity
    float theta = dot(lightDirN, normalize(-vec3(light.view * vec4(light.direction, 1.0f))));
    float epsilon = light.cutOff - light.outerCutOff;
    float intensity = clamp((theta - light.outerCutOff) / epsilon, 0.0, 1.0);
    // combine results
    vec3 ambient = light.color * texture(diffuseTexture, fTexCoords).rgb;
    vec3 diffuse = diff * light.color * texture(diffuseTexture, fTexCoords).rgb;
    vec3 specular = specCoeff * light.color * texture(specularTexture, fTexCoords).rgb;

    ambient *= attenuation * intensity;;
    diffuse *= attenuation * intensity;;
    specular *= attenuation * intensity;;

    return clamp((ambient + diffuse + specular), 0.0f, 1.0f);
}

float computeFog()
{
    float fragmentDistance = length(fPosition);
    float fogFactor = exp(-pow(fragmentDistance * fogDensity, 2));
    return clamp(fogFactor, 0.0f, 1.0f);

}

void main() 
{
   vec3 color= computeDirLight();
    vec4 texColorDifusse=texture(diffuseTexture,fTexCoords);
    if(texColorDifusse.a <0.0005){
        discard;
    }
    color+= computePointLight();
    for (int i = 0; i < NR_SPOT_LIGHTS; i++) {
        color += computeSpotLight(spotlight[i]);
    }
    float fogFactor = computeFog();
    vec4 fogColor = vec4(0.5f, 0.5f, 0.5f, 1.0f);
    fColor = mix(fogColor, vec4(color, 1.0f), fogFactor);
}
