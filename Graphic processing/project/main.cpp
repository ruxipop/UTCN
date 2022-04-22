#define GLEW_STATIC
#include <OpenGL/gl3.h>
#include <GLFW/glfw3.h>
#include "glm/glm.hpp" //core glm functionality
#include "glm/gtc/matrix_transform.hpp" //glm extension for generating common transformation matrices
#include "glm/gtc/matrix_inverse.hpp" //glm extension for computing inverse matrices
#include "glm/gtc/type_ptr.hpp" //glm extension for accessing the internal data structure of glm types

#include "Window.h"
#include "Shader.hpp"
#include "Camera.hpp"
#include "Model3D.hpp"
#include "SkyBox.hpp"
#include <iostream>


// window
gps::Window myWindow;

// matrices
glm::mat4 model;

glm::mat4 lightModel;
glm::mat4 view;
glm::mat4 projection;
glm::mat3 normalMatrix;

// light parameters
glm::vec3 lightDir;
glm::vec3 lightColor;
glm::vec3 lightPos;
glm::vec3 lightPosColor;

// shader uniform locations
GLuint modelLoc;
GLuint viewLoc;
GLuint projectionLoc;
GLuint normalMatrixLoc;
GLuint lightDirLoc;
GLuint lightColorLoc;
GLint lightPosLoc;
GLuint lightColorPosLoc;
//shadow
GLuint shadowMapFBO;
GLuint depthMapTexture;
bool showDepthMap;

// mouse
bool firstMouse = true;
float lastX;
float lastY;
float pitch = 0.0f;

//spotlight
const int NR_SPOT_LIGHTS = 2;
glm::vec3 lampColor =  glm::vec3(1.0f, 1.0f, 1.0f);

// camera
gps::Camera myCamera(
                     glm::vec3(0.0f, 8.0f, 4.0f),
                     glm::vec3(0.0f, 2.0f, 10.0f),
                     glm::vec3(0.0f, 1.0f, 0.0f));

GLfloat cameraSpeed = 0.1f;

GLboolean pressedKeys[1024];

bool isCameraAn = false;
gps::Camera anCamera(
                     glm::vec3(-10.0f, 40.0f, -90.0f),
                     glm::vec3(0.0f, 0.0f, 0.0f),
                     glm::vec3(0.0f, 1.0f, 0.0f));



// models
gps::Model3D map;
gps::Model3D lightCube;
gps::Model3D screenQuad;
gps::Model3D characterStanding;
gps::Model3D characterCrouch;
gps::Model3D characterJump1;
gps::Model3D characterJump2;
gps::Model3D helicopter;
gps::Model3D helicopterB;
GLfloat angle;
glm::mat4 modelHelicopter;
glm::mat4 modelHelicopterBlades;

// shaders
gps::Shader myBasicShader;
gps::Shader depthMapShader;
gps::Shader screenQuadShader;
gps::Shader lightShader;
gps::Shader skyboxShader;
gps::SkyBox skyNight;
gps::SkyBox skyDay;



// fog
float fogDensity = 0.0f;
GLuint fogDensityLoc;
GLuint fogDensityLoc1;

//night/day
bool isNight=false;
bool isLight=false;

//animation helicopter
float delta = 0;
float deltaAngle = 0;
float helicopterBlAngle = 0;
float movementSpeed = 0.02f;
float angleSpeed = 20;
enum moveA
{
    MOVE,
    ROTATE,
    CHANGE,
    STOP
    
};


glm::vec3 helicopterPos(0.0f, 0.0f, 0.0f);
moveA animation1=MOVE;
moveA animation = MOVE;
float helicopterAngle = 0;
bool forward= true;
bool right = true;
//codul din lab pentru animatie
void updateDelta(double elapsedSeconds)
{
    delta = delta+movementSpeed * elapsedSeconds ;
    deltaAngle = angleSpeed* elapsedSeconds*100 ;
}
double lastTimeStamp = glfwGetTime();

GLenum glCheckError_(const char *file, int line)
{
    GLenum errorCode;
    while ((errorCode = glGetError()) != GL_NO_ERROR) {
        std::string error;
        switch (errorCode) {
            case GL_INVALID_ENUM:
                error = "INVALID_ENUM";
                break;
            case GL_INVALID_VALUE:
                error = "INVALID_VALUE";
                break;
            case GL_INVALID_OPERATION:
                error = "INVALID_OPERATION";
                break;
                
            case GL_OUT_OF_MEMORY:
                error = "OUT_OF_MEMORY";
                break;
            case GL_INVALID_FRAMEBUFFER_OPERATION:
                error = "INVALID_FRAMEBUFFER_OPERATION";
                break;
        }
        std::cout << error << " | " << file << " (" << line << ")" << std::endl;
    }
    return errorCode;
}
#define glCheckError() glCheckError_(__FILE__, __LINE__)

void windowResizeCallback(GLFWwindow* window, int width, int height) {
    fprintf(stdout, "Window resized! New width: %d , and height: %d\n", width, height);
    glfwGetFramebufferSize(window, &width, &height);
    glViewport(0, 0, width, height);
    projection = glm::perspective(glm::radians(45.0f), (float)width / (float)height, 0.1f, 1000.0f);
    projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
    glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));
}

void keyboardCallback(GLFWwindow* window, int key, int scancode, int action, int mode) {
    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
        glfwSetWindowShouldClose(window, GL_TRUE);
    }
    
    if (key == GLFW_KEY_V && action == GLFW_PRESS) {
        isCameraAn = !isCameraAn;
        if (animation== STOP){
            animation=animation1;
        }
        else{
            animation1=animation;
            animation=STOP;
            
        }
    }
    if (key == GLFW_KEY_N && action == GLFW_PRESS) {
        
        isNight =!isNight;
        isLight = isNight;
    }
    
    if (key == GLFW_KEY_M && action == GLFW_PRESS)
        showDepthMap = !showDepthMap;
    
    
    if (key == GLFW_KEY_1 && action == GLFW_PRESS) {  //wireframe
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }
    
    if (key == GLFW_KEY_2 && action == GLFW_PRESS) {  //solid
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }
    
    
    if (key == GLFW_KEY_3 && action == GLFW_PRESS) { //poligonal
        glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
    }
    
    if (key == GLFW_KEY_8 && action == GLFW_PRESS) {
        fogDensity-= 0.03f;
        glUniform1f(fogDensityLoc, fogDensity);
    }
    if (key == GLFW_KEY_9 && action == GLFW_PRESS) {
        fogDensity += 0.03f;
        glUniform1f(fogDensityLoc, fogDensity);
    }
    
    
    if (key >= 0 && key < 1024) {
        if (action == GLFW_PRESS) {
            pressedKeys[key] = true;
        } else if (action == GLFW_RELEASE) {
            pressedKeys[key] = false;
        }
    }
}

void mouseCallback(GLFWwindow* window, double xpos, double ypos) {
    
    if (firstMouse)
    {
        lastX = xpos;
        lastY = ypos;
        firstMouse = false;
    }
    
    float xoffset = xpos - lastX;
    float yoffset = lastY - ypos;
    lastX = xpos;
    lastY = ypos;
    
    float sensitivity = 0.1f;
    xoffset *= sensitivity;
    yoffset *= sensitivity;
    
    angle += xoffset;
    pitch += yoffset;
    
    if (pitch > 89.0f)
        pitch = 89.0f;
    if (pitch < -89.0f)
        pitch = -89.0f;
    
    myCamera.rotate(pitch, angle);
    view = myCamera.getViewMatrix();
    myBasicShader.useShaderProgram();
    glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
}

void processMovement() {
    if (pressedKeys[GLFW_KEY_W]) {
        myCamera.move(gps::MOVE_FORWARD, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for map
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    }
    
    if (pressedKeys[GLFW_KEY_S]) {
        myCamera.move(gps::MOVE_BACKWARD, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for map
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    }
    
    if (pressedKeys[GLFW_KEY_A]) {
        myCamera.move(gps::MOVE_LEFT, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for map
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    }
    
    if (pressedKeys[GLFW_KEY_D]) {
        myCamera.move(gps::MOVE_RIGHT, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for map
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    }
    
    if (pressedKeys[GLFW_KEY_SPACE]) {
        myCamera.move(gps::MOVE_UP, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for map
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
    }
    if (pressedKeys[GLFW_KEY_LEFT_SHIFT]) {
        myCamera.move(gps::MOVE_DOWN, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for map
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
    }
    
    if (pressedKeys[GLFW_KEY_Q]) {
        angle -= 1.0f;
        //   update model matrix for map
        myCamera.rotate(0.0f, angle);
        
        view = myCamera.getViewMatrix();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        
        // update normal matrix for map
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    }
    
    if (pressedKeys[GLFW_KEY_E]) {
        angle += 1.0f;
        // update model matrix for map
        myCamera.rotate(0.0f, angle);
        
        view = myCamera.getViewMatrix();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        
        // update normal matrix for map
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    }
    
    if (isCameraAn) {
        anCamera.move(gps::MOVE_RIGHT, cameraSpeed);
        
        anCamera.setCameraTarget(glm::vec3(0.0f, 4.0f, 0.0f));
        
        view = anCamera.getViewMatrix();
        
        
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
        
    }
    
    
}

void initOpenGLWindow() {
    myWindow.Create(1920, 1017, "Open Project");
}

void setWindowCallbacks() {
    glfwSetWindowSizeCallback(myWindow.getWindow(), windowResizeCallback);
    glfwSetKeyCallback(myWindow.getWindow(), keyboardCallback);
    glfwSetCursorPosCallback(myWindow.getWindow(), mouseCallback);
}

void initOpenGLState() {
    glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
    glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);
    glEnable(GL_FRAMEBUFFER_SRGB);
    glEnable(GL_DEPTH_TEST); // enable depth-testing
    glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
    glEnable(GL_CULL_FACE); // cull face
    glCullFace(GL_BACK); // cull back face
    glFrontFace(GL_CCW); // GL_CCW for counter clock-wise
}

void initModels() {
    
    map.LoadModel("models/scena.obj");
    helicopter.LoadModel("models/Models/Helicopters/helicopter1/helicopter-simple.obj");
    helicopterB.LoadModel("models/Models/Helicopters/helicopter1/helicopter-bl.obj");
    screenQuad.LoadModel("models/quad/quad.obj");
    
}

void initShaders() {
    myBasicShader.loadShader(
                             "shaders/basic.vert",
                             "shaders/basic.frag");
    myBasicShader.useShaderProgram();
    depthMapShader.loadShader(
                              "shaders/shadow.vert",
                              "shaders/shadow.frag"
                              );
    depthMapShader.useShaderProgram();
    screenQuadShader.loadShader(
                                "shaders/screenQuad.vert",
                                "shaders/screenQuad.frag"
                                );
    screenQuadShader.useShaderProgram();
    lightShader.loadShader(
                           "shaders/lightCube.vert",
                           "shaders/lightCube.frag"
                           );
    lightShader.useShaderProgram();
    skyboxShader.loadShader("shaders/skyboxShader.vert", "shaders/skyboxShader.frag");
    skyboxShader.useShaderProgram();
}

void initUniforms() {
    myBasicShader.useShaderProgram();
    model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0.0f, 1.0f, 0.0f));
    modelLoc = glGetUniformLocation(myBasicShader.shaderProgram, "model");
    
    // get view matrix for current camera
    view = myCamera.getViewMatrix();
    viewLoc = glGetUniformLocation(myBasicShader.shaderProgram, "view");
    // send view matrix to shader
    glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
    
    // compute normal matrix for map
    normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    normalMatrixLoc = glGetUniformLocation(myBasicShader.shaderProgram, "normalMatrix");
    
    // create projection matrix
    projection = glm::perspective(glm::radians(45.0f),
                                  (float)myWindow.getWindowDimensions().width / (float)myWindow.getWindowDimensions().height,
                                  0.1f, 1000.0f);
    projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
    // send projection matrix to shader
    glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));
    
    
    //  fog
    fogDensityLoc = glGetUniformLocation(myBasicShader.shaderProgram, "fogDensity");
    //   fogDensityLoc1 = glGetUniformLocation(skyboxShader.shaderProgram, "fogDensity");
    glUniform1f(fogDensityLoc, fogDensity);
    //   glUniform1f(fogDensityLoc1, fogDensity);
    lightShader.useShaderProgram();
    glUniformMatrix4fv(glGetUniformLocation(lightShader.shaderProgram, "projection"), 1, GL_FALSE, glm::value_ptr(projection));
}


void initFBO() {
    glGenFramebuffers(1, &shadowMapFBO);
    //create depth texture for FBO
    glGenTextures(1, &depthMapTexture);
    glBindTexture(GL_TEXTURE_2D, depthMapTexture);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT,
                 2048, 2048, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    float borderColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
    
    glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMapTexture,
                           0);
    
    glDrawBuffer(GL_NONE);
    glReadBuffer(GL_NONE);
    
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
}

glm::mat4 computeLightSpaceTrMatrix() {
    glm::mat4 lightView = glm::lookAt(lightDir, glm::vec3(0.0f), glm::vec3(0.0f, 1.0f, 0.0f));
    
    const GLfloat near_plane = 0.2f, far_plane = 200.0f;
    glm::mat4 lightProjection = glm::ortho(-60.0f, 40.0f, -20.0f, 40.0f, near_plane, far_plane);
    
    glm::mat4 lightSpaceTrMatrix = lightProjection * lightView;
    
    return lightSpaceTrMatrix;
}



void renderMap(gps::Shader shader,bool depthPass) {
    // select active shader program
    shader.useShaderProgram();
    model = glm::mat4(1.0f);
    //send map model matrix data to shader
    
    glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
    
    if (!depthPass)
    {    //send map normal matrix data to shader
        glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    }
    
    // draw map
    map.Draw(shader);
}

void renderShadowMap() {
    //render the scene to the depth buffer
    depthMapShader.useShaderProgram();
    glUniformMatrix4fv(glGetUniformLocation(depthMapShader.shaderProgram, "lightSpaceTrMatrix"),
                       1,
                       GL_FALSE,
                       glm::value_ptr(computeLightSpaceTrMatrix()));
    glViewport(0, 0, 2048, 2048);
    glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
    glClear(GL_DEPTH_BUFFER_BIT);
    renderMap(depthMapShader, true);
    
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    
}



void renderAnimationHelicopter(gps::Shader shader)
{
    shader.useShaderProgram();
    
    double currentTimeStamp = glfwGetTime();
    if(animation==STOP){
        updateDelta(0);//daca ii stop timpul curent din aplicatie ii 0
        
    }else{
        updateDelta(currentTimeStamp - lastTimeStamp);
        
    }
    lastTimeStamp = currentTimeStamp;
    
    switch (animation)
    {
            
        case MOVE:
        {
            if (forward)
            {
                modelHelicopter = glm::translate(modelHelicopter, glm::vec3(0.0f, 0.0f, -delta));
                helicopterPos += glm::vec3(0.0f, 0.0f, -delta);
                if (helicopterPos.z < -movementSpeed * 3000)
                {
                    
                    helicopterAngle = 0;
                    forward= !forward;
                    
                    animation = ROTATE;
                    helicopterPos = glm::vec3(0.0f);
                }
                
            }
            else
            {
                modelHelicopter = glm::translate(modelHelicopter, glm::vec3(0.0f, 0.0f, -delta));
                helicopterPos += glm::vec3(0.0f, 0.0f, delta);
                if (helicopterPos.z > 0)
                {
                    
                    helicopterAngle = 0;
                    forward = !forward;
                    animation =ROTATE;
                    helicopterPos= glm::vec3(-movementSpeed * 3000);
                }
                
                
            }
            break;
        }
        case CHANGE:
        {
            if (right)
            {
                modelHelicopter = glm::translate(modelHelicopter, glm::vec3(0.0f, 0.0f, -delta));
                helicopterPos += glm::vec3(0.0f, 0.0f, delta);
                if (helicopterPos.z > 0)
                {
                    
                    helicopterAngle = 0;
                    animation = ROTATE;
                    right = !right;
                    helicopterPos = glm::vec3(0.0f);
                }
            }
            else
            {
                modelHelicopter = glm::translate(modelHelicopter, glm::vec3(0.0f, 0.0f, -delta));
                helicopterPos += glm::vec3(0.0f, 0.0f, -delta);
                if (helicopterPos.z < -movementSpeed * 3000)
                {
                    
                    helicopterAngle = 0;
                    right = !right;
                    animation = ROTATE;
                }
            }
            break;
        }
            
            
        case ROTATE:
        {
            modelHelicopter = glm::rotate(modelHelicopter, glm::radians(3*delta), glm::vec3(0.0f, 1.0f, 0.0f));
            helicopterAngle += 3*delta;
            if (helicopterAngle > 45.0f) // face intoarcere la 45 grade
            {
                animation = CHANGE;
                helicopterAngle = 0;
            }
            break;
        }
            
            
        case STOP:{
            
        }
    }
    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(modelHelicopter));
    helicopter.Draw(shader);
    helicopterBlAngle += deltaAngle;
    modelHelicopterBlades = glm::rotate(modelHelicopter, glm::radians(helicopterBlAngle), glm::vec3(0.0f, 1.0f, 0.0f)); //pentru elici /.matricea lor
    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(modelHelicopterBlades));
    helicopterB.Draw(shader);
}

void renderScene() {
    if(isNight){
        
        lightDir = glm::vec3(40.0f, 35.0f, 60.0f);
        lightColor = glm::vec3(0.1f, 0.1f, 0.1f);
        
        
    }
    else{
        
        lightDir = glm::vec3(-40.0f, 35.0f, 60.0f);
        lightColor = glm::vec3(1.0f, 1.0f, 1.0f);
    }
    
    if(isLight){//point
        
        
        lightPosColor = glm::vec3(1.0f, 0.0f, 0.0f);
        
    }
    else{
        lightPosColor = glm::vec3(0.0f, 0.0f, 0.0f);
    }
    
    
    lightPos = glm::vec3(-24.744722f, 9.778681f, 33.420479f);
    
    lightPosLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightPosEye");
    
    glUniform3fv(lightPosLoc, 1, glm::value_ptr(lightPos));
    
    lightColorPosLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightColorPos");
    // send light color to shader
    glUniform3fv(lightColorPosLoc, 1, glm::value_ptr(lightPosColor));
    
    lightModel = glm::translate(glm::mat4(1.0f), lightDir);
    lightModel = glm::scale(lightModel, glm::vec3(2.0f, 2.0f, 2.0f));
    lightDirLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightDir");
    // send light dir to shader
    glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view * lightModel)) * lightDir));
    
    lightColorLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor");
    // send light color to shader
    glUniform3fv(lightColorLoc, 1, glm::value_ptr(lightColor));
    
    renderShadowMap();
    glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);
    
    if (showDepthMap) {
        
        glClear(GL_COLOR_BUFFER_BIT);
        
        screenQuadShader.useShaderProgram();
        
        //bind the depth map
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, depthMapTexture);
        glUniform1i(glGetUniformLocation(screenQuadShader.shaderProgram, "depthMap"), 0);
        
        glDisable(GL_DEPTH_TEST);
        screenQuad.Draw(screenQuadShader);
        glEnable(GL_DEPTH_TEST);
    }
    else {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        myBasicShader.useShaderProgram();
        //bind the shadow map (for the uniform)
        glActiveTexture(GL_TEXTURE3);
        glBindTexture(GL_TEXTURE_2D, depthMapTexture);
        glUniform1i(glGetUniformLocation(myBasicShader.shaderProgram, "shadowMap"), 3);
        
        
        glUniformMatrix4fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightSpaceTrMatrix"),
                           1,
                           GL_FALSE,
                           glm::value_ptr(computeLightSpaceTrMatrix()));
        
        //render the scenes
        renderMap(myBasicShader, false);
        renderAnimationHelicopter(myBasicShader);
        
        
    }
    
    if (isNight)
        skyNight.Draw(skyboxShader, view, projection);
    else
        skyDay.Draw(skyboxShader, view, projection);
    
    
    
    
    
}

void cleanup() {
    myWindow.Delete();
    
}

void updateSpotlights()
{
    myBasicShader.useShaderProgram();
    
    for (int i = 0; i < NR_SPOT_LIGHTS; i++) {
        char buffer[64];
        
        
        sprintf(buffer, "spotlight[%i].constant", i);
        glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, buffer), 1.0f);
        
        sprintf(buffer, "spotlight[%i].linear", i);
        glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, buffer), 0.14f);
        
        sprintf(buffer, "spotlight[%i].quadratic", i);
        glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, buffer),0.32f);
        
        sprintf(buffer, "spotlight[%i].cutOff", i);
        glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, buffer), glm::cos(glm::radians(8.5f)));
        
        sprintf(buffer, "spotlight[%i].outerCutOff", i);
        glUniform1f(glGetUniformLocation(myBasicShader.shaderProgram, buffer), glm::cos(glm::radians(12.5f)));
        
        
        
        sprintf(buffer, "spotlight[%i].color", i);
        if (isLight){
            glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, buffer), 1, glm::value_ptr(lampColor));}
        else{
            glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, buffer), 1, glm::value_ptr(glm::vec3(0.0f)));}
        
    }
    glm::vec3 target = glm::vec3(-10.5 + 6.75f,  -19.73, -20.15 + 0.75f);
    glm::vec3 position = glm::vec3(-10.5, 19.5,-20.15);
    glm::mat4 lightView = glm::lookAt(position, target, glm::vec3(0.0f, 1.0f, 0.0f));
    glm::mat3 lightNormalMatrix = glm::mat3(glm::inverseTranspose(lightView * model));
    
    glUniformMatrix3fv(glGetUniformLocation(myBasicShader.shaderProgram, "spotlight[0].normalM"), 1, GL_FALSE, glm::value_ptr(lightNormalMatrix));
    glUniformMatrix4fv(glGetUniformLocation(myBasicShader.shaderProgram, "spotlight[0].view"), 1, GL_FALSE, glm::value_ptr(lightView));
    glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "spotlight[0].position"), 1, glm::value_ptr(position));
    glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "spotlight[0].direction"), 1, glm::value_ptr(target));
    
    target = glm::vec3(15.8f - 2.75f, 0.0f, 30.3f+ 2.75f);
    position = glm::vec3(15.8f, 19.7f, 30.3f);
    lightView = glm::lookAt(position, target, glm::vec3(0.0f, 1.0f, 0.0f));
    lightNormalMatrix = glm::mat3(glm::inverseTranspose(lightView * model));
    
    glUniformMatrix3fv(glGetUniformLocation(myBasicShader.shaderProgram, "spotlight[1].normalM"), 1, GL_FALSE, glm::value_ptr(lightNormalMatrix));
    glUniformMatrix4fv(glGetUniformLocation(myBasicShader.shaderProgram, "spotlight[1].view"), 1, GL_FALSE, glm::value_ptr(lightView));
    glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "spotlight[1].position"), 1, glm::value_ptr(position));
    glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "spotlight[1].direction"), 1, glm::value_ptr(target));
    
}


void initSkyBox()
{
    std::vector<const GLchar*> faces;
    
    faces.push_back("skybox/day/right.png");
    faces.push_back("skybox/day/left.png");
    faces.push_back("skybox/day/top.png");
    faces.push_back("skybox/day/bottom.png");
    faces.push_back("skybox/day/back.png");
    faces.push_back("skybox/day/front.png");
    
    skyDay.Load(faces);
    faces.clear();
    
    
    faces.push_back("skybox/night/right.png");
    faces.push_back("skybox/night/left.png");
    faces.push_back("skybox/night/top.png");
    faces.push_back("skybox/night/bottom.png");
    faces.push_back("skybox/night/back.png");
    faces.push_back("skybox/night/front.png");
    
    
    skyNight.Load(faces);
    
}
int main(int argc, const char * argv[]) {
    
    try {
        initOpenGLWindow();
    } catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
        return EXIT_FAILURE;
    }
    
    initOpenGLState();
    initModels();
    initShaders();
    initUniforms();
    initFBO();
    initSkyBox();
    setWindowCallbacks();
    
    glCheckError();
    // application loop
    while (!glfwWindowShouldClose(myWindow.getWindow())) {
        processMovement();
        renderScene();
        updateSpotlights();
        glfwPollEvents();
        glfwSwapBuffers(myWindow.getWindow());
        
        glCheckError();
    }
    
    cleanup();
    
    return EXIT_SUCCESS;
}
