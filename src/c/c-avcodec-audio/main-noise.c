
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#ifdef HAVE_AV_CONFIG_H
#undef HAVE_AV_CONFIG_H
#endif

#include <libavcodec/avcodec.h>
#include <libavutil/mathematics.h>
#include <libavutil/lfg.h>


static void audio_encoding_sample(const char * filename, int content_mode) {
    AVCodec *codec;
    AVCodecContext *c= NULL;
    int frame_size, i, j, out_size, outbuf_size;
    FILE *f;
    size_t samples_byte_size;
    short *samples;
    uint8_t *outbuf;
    
    puts("Audio encoding\n");
    
    /* find the MP3 encoder */
    codec = avcodec_find_encoder(CODEC_ID_MP3);
    if (!codec) {
        fputs("codec not found\n", stderr);
        exit(1);
    }
    
    c = avcodec_alloc_context3(codec);
    
    /* put sample parameters */
    c->bit_rate = 64000;
    c->sample_rate = 44100;
    c->channels = 2;
    c->channel_layout = AV_CH_LAYOUT_STEREO;
    c->sample_fmt = AV_SAMPLE_FMT_S16;

    /* open it */
    if (avcodec_open2(c, codec, NULL) < 0) {
        fprintf(stderr, "could not open codec\n");
        exit(1);
    }
    
    /* the codec gives us the frame size, in samples */
    frame_size = c->frame_size;
    fprintf(stdout, "frame_size = %d\n", frame_size);

    samples_byte_size = frame_size * sizeof(short) * c->channels;
    samples = malloc(samples_byte_size);
    outbuf_size = 10000;
    outbuf = malloc(outbuf_size);
    
    f = fopen(filename, "wb");
    if (!f) {
        fprintf(stderr, "could not open %s\n", filename);
        exit(1);
    }
    
    if (content_mode == 0) {
        /* encode a single tone sound */
        float t, tincr;
        
        t = 0;
        tincr = 2 * M_PI * 440.0 / c->sample_rate;
        for(i=0; i<2000; i++) {
            for(j=0;j<frame_size;j++) {
                samples[2*j] = (int)(sin(t) * 10000);
                samples[2*j+1] = samples[2*j];
                t += tincr;
            }
            /* encode the samples */
            out_size = avcodec_encode_audio(c, outbuf, outbuf_size, samples);
            fwrite(outbuf, 1, out_size, f);
        }
    } else {
        /* encode a random noise */
        AVLFG prng_state;
        av_lfg_init(&prng_state, 0x20120410);
        
        /* encode some samples, 2000=>52sec */
        for (i = 0; i < 2000; ++i) {
            /* note, that 2-channel-wide noise sounds 'softer' than 1-channel-wide */
            int samples_uints = (samples_byte_size / sizeof(uint32_t));
            uint32_t * buf_u32 = (uint32_t *) samples;
            for (j = 0; j < samples_uints; ++j) {
                buf_u32[j] = av_lfg_get(&prng_state);
            }
            
            /* encode the samples */
            out_size = avcodec_encode_audio(c, outbuf, outbuf_size, samples);
            fwrite(outbuf, 1, out_size, f);
        }
        
    }
    
    
    fclose(f);
    free(outbuf);
    free(samples);
    
    avcodec_close(c);
    av_free(c);
}

int main(int argc, char ** argv) {
    puts("Mew 1.0\n");
    
    /* register all the codecs */
    avcodec_register_all();
    
    puts("Avcodec initialized\n");
    
    audio_encoding_sample("preved.mp3", 0);
    return 0;
}

