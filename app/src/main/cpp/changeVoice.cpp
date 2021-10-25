#include <unistd.h>
#include "com_androiddemoax_bianshengboygirl_Changevoices.h"
using namespace FMOD;

extern "C"
JNIEXPORT
void JNICALL
Java_com_androiddemoax_bianshengboygirl_Changevoices_fixVoice(JNIEnv * env, jobject, jint type,jstring path) {
    const char * path_ = env->GetStringUTFChars(path,NULL);
    float frequency=0;
    System * system = 0;
    Sound * sound = 0;
    Channel * channel= 0;
    DSP * dsp = 0;
    System_Create(&system);
//    系统初始化
    system->init(32,com_androiddemoax_bianshengboygirl_Changevoices_MODE_NORMAL,0);
//    创建声音
    system->createSound(path_,FMOD_DEFAULT,0,&sound);
    //播放原声音
    //channel->setVolume(1.0f);
   //system->playSound(sound,0,false,&channel);


switch (type) {

        case com_androiddemoax_bianshengboygirl_Changevoices_MODE_LUOLI:
            //萝莉
            //DSP digital signal process
            //dsp -> 音效 创建fmod中预定义好的音效
            //FMOD_DSP_TYPE_PITCHSHIFT dsp，提升或者降低音调用的一种音效
            system->createDSPByType( FMOD_DSP_TYPE_PITCHSHIFT,&dsp);
            //system->createDSPByType( FMOD_DSP_TYPE_TREMOLO,&dsp);//颤音

            //设置音调的参数
            dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH,2);

            system->playSound(sound, 0, false, &channel);

            channel->getFrequency(&frequency);
            frequency = frequency *0.79;
            channel->setFrequency(frequency);
            //添加到channel

            channel->addDSP(0,dsp);
        break;


        case com_androiddemoax_bianshengboygirl_Changevoices_MODE_DASHU:
        //大叔
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT,&dsp);
            dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH,0.7);
            system->playSound(sound, 0, false, &channel);

            channel->getFrequency(&frequency);
            frequency = frequency *1.7;
            channel->setFrequency(frequency);
                //添加到channel
            channel->addDSP(0,dsp);
            //LOGI("%s","fix dashu");
        break;

        default:
            break;

        }

    system->update();

    bool playing =true ;
    while (playing){
        channel -> isPlaying(&playing);
        usleep(1000 * 1000);
    }
    sound->release();
    system->close();

    system->release();
    env->ReleaseStringUTFChars(path,path_);






}



