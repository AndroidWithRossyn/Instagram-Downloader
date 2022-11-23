package com.banrossyn.ininsta.story.downloader.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.banrossyn.ininsta.story.downloader.R;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

public class VideoControllerView extends FrameLayout {
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private static final String TAG = "VideoControllerView";
    private static final int sDefaultTimeout = 3000;
    private ViewGroup mAnchor;
    private Context mContext;
    private TextView mCurrentTime;
    private boolean mDragging;
    private TextView mEndTime;
    private ImageView mFfwdButton;
    private OnClickListener mFfwdListener;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private boolean mFromXml;
    private ImageView mFullscreenButton;
    private OnClickListener mFullscreenListener;
    private Handler mHandler;
    private boolean mListenersSet;
    private ImageView mNextButton;
    private OnClickListener mNextListener;
    private ImageView mPauseButton;
    private OnClickListener mPauseListener;
    private MediaPlayerControl mPlayer;
    private ImageView mPrevButton;
    private OnClickListener mPrevListener;
    private ProgressBar mProgress;
    private ImageView mRewButton;
    private OnClickListener mRewListener;
    private View mRoot;
    private SeekBar.OnSeekBarChangeListener mSeekListener;
    private boolean mShowing;
    private boolean mUseFastForward;

    public interface MediaPlayerControl {
        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        int getBufferPercentage();

        int getCurrentPosition();

        int getDuration();

        boolean isFullScreen();

        boolean isPlaying();

        void pause();

        void seekTo(int i);

        void start();

        void stop();

        void toggleFullScreen();
    }

    public VideoControllerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandler = new MessageHandler(this);
        this.mPauseListener = new OnClickListener() {
            public void onClick(View view) {
                VideoControllerView.this.doPauseResume();
                VideoControllerView.this.show(3000);
            }
        };
        this.mFullscreenListener = new OnClickListener() {
            public void onClick(View view) {
                VideoControllerView.this.doToggleFullscreen();
                VideoControllerView.this.show(3000);
            }
        };
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
                VideoControllerView.this.show(3600000);
                VideoControllerView.this.mDragging = true;
                VideoControllerView.this.mHandler.removeMessages(2);
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (VideoControllerView.this.mPlayer != null && z) {
                    int duration = (int) ((((long) VideoControllerView.this.mPlayer.getDuration()) * ((long) i)) / 1000);
                    VideoControllerView.this.mPlayer.seekTo(duration);
                    if (VideoControllerView.this.mCurrentTime != null) {
                        VideoControllerView.this.mCurrentTime.setText(VideoControllerView.this.stringForTime(duration));
                    }
                }
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                VideoControllerView.this.mDragging = false;
                VideoControllerView.this.setProgress();
                VideoControllerView.this.updatePausePlay();
                VideoControllerView.this.show(3000);
                VideoControllerView.this.mHandler.sendEmptyMessage(2);
            }
        };
        this.mRewListener = new OnClickListener() {
            public void onClick(View view) {
                if (VideoControllerView.this.mPlayer != null) {
                    VideoControllerView.this.mPlayer.seekTo(VideoControllerView.this.mPlayer.getCurrentPosition() - 5000);
                    VideoControllerView.this.setProgress();
                    VideoControllerView.this.show(3000);
                }
            }
        };
        this.mFfwdListener = new OnClickListener() {
            public void onClick(View view) {
                if (VideoControllerView.this.mPlayer != null) {
                    VideoControllerView.this.mPlayer.seekTo(VideoControllerView.this.mPlayer.getCurrentPosition() + 15000);
                    VideoControllerView.this.setProgress();
                    VideoControllerView.this.show(3000);
                }
            }
        };
        this.mRoot = null;
        this.mContext = context;
        this.mUseFastForward = true;
        this.mFromXml = true;
        Log.i(TAG, TAG);
    }

    public VideoControllerView(Context context, boolean z) {
        super(context);
        this.mHandler = new MessageHandler(this);
        this.mPauseListener = new OnClickListener() {
            public void onClick(View view) {
                VideoControllerView.this.doPauseResume();
                VideoControllerView.this.show(3000);
            }
        };
        this.mFullscreenListener = new OnClickListener() {
            public void onClick(View view) {
                VideoControllerView.this.doToggleFullscreen();
                VideoControllerView.this.show(3000);
            }
        };
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
                VideoControllerView.this.show(3600000);
                VideoControllerView.this.mDragging = true;
                VideoControllerView.this.mHandler.removeMessages(2);
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (VideoControllerView.this.mPlayer != null && z) {
                    int duration = (int) ((((long) VideoControllerView.this.mPlayer.getDuration()) * ((long) i)) / 1000);
                    VideoControllerView.this.mPlayer.seekTo(duration);
                    if (VideoControllerView.this.mCurrentTime != null) {
                        VideoControllerView.this.mCurrentTime.setText(VideoControllerView.this.stringForTime(duration));
                    }
                }
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                VideoControllerView.this.mDragging = false;
                VideoControllerView.this.setProgress();
                VideoControllerView.this.updatePausePlay();
                VideoControllerView.this.show(3000);
                VideoControllerView.this.mHandler.sendEmptyMessage(2);
            }
        };
        this.mRewListener = new OnClickListener() {

            public void onClick(View view) {
                if (VideoControllerView.this.mPlayer != null) {
                    VideoControllerView.this.mPlayer.seekTo(VideoControllerView.this.mPlayer.getCurrentPosition() - 5000);
                    VideoControllerView.this.setProgress();
                    VideoControllerView.this.show(3000);
                }
            }
        };
        this.mFfwdListener = new OnClickListener() {

            public void onClick(View view) {
                if (VideoControllerView.this.mPlayer != null) {
                    VideoControllerView.this.mPlayer.seekTo(VideoControllerView.this.mPlayer.getCurrentPosition() + 15000);
                    VideoControllerView.this.setProgress();
                    VideoControllerView.this.show(3000);
                }
            }
        };
        this.mContext = context;
        this.mUseFastForward = z;
        Log.i(TAG, TAG);
    }

    public VideoControllerView(Context context) {
        this(context, true);
        Log.i(TAG, TAG);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        View view = this.mRoot;
        if (view != null) {
            initControllerView(view);
        }
    }

    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        this.mPlayer = mediaPlayerControl;
        updatePausePlay();
        updateFullScreen();
    }

    public void setAnchorView(ViewGroup viewGroup) {
        this.mAnchor = viewGroup;
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        removeAllViews();
        addView(makeControllerView(), layoutParams);
    }

    public View makeControllerView() {
        @SuppressLint("WrongConstant") View inflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.layout_controller, (ViewGroup) null);
        this.mRoot = inflate;
        initControllerView(inflate);
        return this.mRoot;
    }

    private void initControllerView(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.pause);
        this.mPauseButton = imageView;
        if (imageView != null) {
            imageView.requestFocus();
            this.mPauseButton.setOnClickListener(this.mPauseListener);
        }
        ImageView imageView2 = (ImageView) view.findViewById(R.id.fullscreen);
        this.mFullscreenButton = imageView2;
        if (imageView2 != null) {
            imageView2.requestFocus();
            this.mFullscreenButton.setOnClickListener(this.mFullscreenListener);
        }
        ImageView imageView3 = (ImageView) view.findViewById(R.id.ffwd);
        this.mFfwdButton = imageView3;
        int i = 0;
        if (imageView3 != null) {
            imageView3.setOnClickListener(this.mFfwdListener);
            if (!this.mFromXml) {
                this.mFfwdButton.setVisibility(this.mUseFastForward ? VISIBLE : GONE);
            }
        }
        ImageView imageView4 = (ImageView) view.findViewById(R.id.rew);
        this.mRewButton = imageView4;
        if (imageView4 != null) {
            imageView4.setOnClickListener(this.mRewListener);
            if (!this.mFromXml) {
                ImageView imageView5 = this.mRewButton;
                if (!this.mUseFastForward) {
                    i = 8;
                }
                imageView5.setVisibility(i);
            }
        }
        ImageView imageView6 = (ImageView) view.findViewById(R.id.next);
        this.mNextButton = imageView6;
        if (imageView6 != null && !this.mFromXml && !this.mListenersSet) {
            imageView6.setVisibility(GONE);
        }
        ImageView imageView7 = (ImageView) view.findViewById(R.id.prev);
        this.mPrevButton = imageView7;
        if (imageView7 != null && !this.mFromXml && !this.mListenersSet) {
            imageView7.setVisibility(GONE);
        }
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.mediacontroller_progress);
        this.mProgress = seekBar;
        if (seekBar != null) {
            if (seekBar instanceof SeekBar) {
                seekBar.setOnSeekBarChangeListener(this.mSeekListener);
            }
            this.mProgress.setMax(1000);
        }
        this.mEndTime = (TextView) view.findViewById(R.id.time);
        this.mCurrentTime = (TextView) view.findViewById(R.id.time_current);
        this.mFormatBuilder = new StringBuilder();
        this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
        installPrevNextListeners();
    }

    public void show() {
        show(3000);
    }

    private void disableUnsupportedButtons() {
        MediaPlayerControl mediaPlayerControl = this.mPlayer;
        if (mediaPlayerControl != null) {
            try {
                if (this.mPauseButton != null && !mediaPlayerControl.canPause()) {
                    this.mPauseButton.setEnabled(false);
                }
                if (this.mRewButton != null && !this.mPlayer.canSeekBackward()) {
                    this.mRewButton.setEnabled(false);
                }
                if (this.mFfwdButton != null && !this.mPlayer.canSeekForward()) {
                    this.mFfwdButton.setEnabled(false);
                }
            } catch (IncompatibleClassChangeError unused) {
            }
        }
    }

    public void show(int i) {
        if (!this.mShowing && this.mAnchor != null) {
            setProgress();
            ImageView imageView = this.mPauseButton;
            if (imageView != null) {
                imageView.requestFocus();
            }
            disableUnsupportedButtons();
            this.mAnchor.addView(this, new LayoutParams(-1, -2, 80));
            this.mShowing = true;
        }
        updatePausePlay();
        updateFullScreen();
        this.mHandler.sendEmptyMessage(2);
        Message obtainMessage = this.mHandler.obtainMessage(1);
        if (i != 0) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendMessageDelayed(obtainMessage, (long) i);
        }
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    public void hide() {
        ViewGroup viewGroup = this.mAnchor;
        if (viewGroup != null) {
            try {
                viewGroup.removeView(this);
                this.mHandler.removeMessages(2);
            } catch (IllegalArgumentException unused) {
                Log.w("MediaController", "already removed");
            }
            this.mShowing = false;
        }
    }

    private String stringForTime(int i) {
        int i2 = i / 1000;
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        this.mFormatBuilder.setLength(0);
        if (i5 > 0) {
            return this.mFormatter.format("%d:%02d:%02d", Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)).toString();
        }
        return this.mFormatter.format("%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3)).toString();
    }

    private int setProgress() {
        MediaPlayerControl mediaPlayerControl = this.mPlayer;
        if (mediaPlayerControl == null || this.mDragging) {
            return 0;
        }
        int currentPosition = mediaPlayerControl.getCurrentPosition();
        int duration = this.mPlayer.getDuration();
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            if (duration > 0) {
                progressBar.setProgress((int) ((((long) currentPosition) * 1000) / ((long) duration)));
            }
            this.mProgress.setSecondaryProgress(this.mPlayer.getBufferPercentage() * 10);
        }
        TextView textView = this.mEndTime;
        if (textView != null) {
            textView.setText(stringForTime(duration));
        }
        TextView textView2 = this.mCurrentTime;
        if (textView2 != null) {
            textView2.setText(stringForTime(currentPosition));
        }
        return currentPosition;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        show(3000);
        return true;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        show(3000);
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mPlayer == null) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getRepeatCount() == 0 && keyEvent.getAction() == 0;
        if (keyCode == 79 || keyCode == 85 || keyCode == 62) {
            if (z) {
                doPauseResume();
                show(3000);
                ImageView imageView = this.mPauseButton;
                if (imageView != null) {
                    imageView.requestFocus();
                }
            }
            return true;
        } else if (keyCode == 126) {
            if (z && !this.mPlayer.isPlaying()) {
                this.mPlayer.start();
                updatePausePlay();
                show(3000);
            }
            return true;
        } else if (keyCode == 86 || keyCode == 127) {
            if (z && this.mPlayer.isPlaying()) {
                this.mPlayer.pause();
                updatePausePlay();
                show(3000);
            }
            return true;
        } else if (keyCode == 25 || keyCode == 24 || keyCode == 164) {
            return super.dispatchKeyEvent(keyEvent);
        } else {
            if (keyCode == 4 || keyCode == 82) {
                if (z) {
                    hide();
                }
                if (this.mPlayer.isPlaying()) {
                    this.mPlayer.stop();
                }
                return true;
            }
            show(3000);
            return super.dispatchKeyEvent(keyEvent);
        }
    }

    public void updatePausePlay() {
        MediaPlayerControl mediaPlayerControl;
        if (this.mRoot != null && this.mPauseButton != null && (mediaPlayerControl = this.mPlayer) != null) {
            if (mediaPlayerControl.isPlaying()) {
                this.mPauseButton.setImageResource(R.drawable.ic_round_pause);
            } else {
                this.mPauseButton.setImageResource(R.drawable.ic_round_play);
            }
        }
    }

    public void updateFullScreen() {
        MediaPlayerControl mediaPlayerControl;
        if (this.mRoot != null && this.mFullscreenButton != null && (mediaPlayerControl = this.mPlayer) != null) {
            if (mediaPlayerControl.isFullScreen()) {
                this.mFullscreenButton.setImageResource(R.drawable.ic_round_fullscreen_exit);
            } else {
                this.mFullscreenButton.setImageResource(R.drawable.ic_round_fullscreen);
            }
        }
    }

    private void doPauseResume() {
        MediaPlayerControl mediaPlayerControl = this.mPlayer;
        if (mediaPlayerControl != null) {
            if (mediaPlayerControl.isPlaying()) {
                this.mPlayer.pause();
            } else {
                this.mPlayer.start();
            }
            updatePausePlay();
        }
    }

    private void doToggleFullscreen() {
        MediaPlayerControl mediaPlayerControl = this.mPlayer;
        if (mediaPlayerControl != null) {
            mediaPlayerControl.toggleFullScreen();
        }
    }

    public void setEnabled(boolean z) {
        ImageView imageView = this.mPauseButton;
        if (imageView != null) {
            imageView.setEnabled(z);
        }
        ImageView imageView2 = this.mFfwdButton;
        if (imageView2 != null) {
            imageView2.setEnabled(z);
        }
        ImageView imageView3 = this.mRewButton;
        if (imageView3 != null) {
            imageView3.setEnabled(z);
        }
        ImageView imageView4 = this.mNextButton;
        boolean z2 = true;
        if (imageView4 != null) {
            imageView4.setEnabled(z && this.mNextListener != null);
        }
        ImageView imageView5 = this.mPrevButton;
        if (imageView5 != null) {
            if (!z || this.mPrevListener == null) {
                z2 = false;
            }
            imageView5.setEnabled(z2);
        }
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setEnabled(z);
        }
        disableUnsupportedButtons();
        super.setEnabled(z);
    }

    private void installPrevNextListeners() {
        ImageView imageView = this.mNextButton;
        boolean z = true;
        if (imageView != null) {
            imageView.setOnClickListener(this.mNextListener);
            this.mNextButton.setEnabled(this.mNextListener != null);
        }
        ImageView imageView2 = this.mPrevButton;
        if (imageView2 != null) {
            imageView2.setOnClickListener(this.mPrevListener);
            ImageView imageView3 = this.mPrevButton;
            if (this.mPrevListener == null) {
                z = false;
            }
            imageView3.setEnabled(z);
        }
    }

    public void setPrevNextListeners(OnClickListener onClickListener, OnClickListener onClickListener2) {
        this.mNextListener = onClickListener;
        this.mPrevListener = onClickListener2;
        this.mListenersSet = true;
        if (this.mRoot != null) {
            installPrevNextListeners();
            ImageView imageView = this.mNextButton;
            if (imageView != null && !this.mFromXml) {
                imageView.setVisibility(VISIBLE);
            }
            ImageView imageView2 = this.mPrevButton;
            if (imageView2 != null && !this.mFromXml) {
                imageView2.setVisibility(VISIBLE);
            }
        }
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<VideoControllerView> mView;

        MessageHandler(VideoControllerView videoControllerView) {
            this.mView = new WeakReference<>(videoControllerView);
        }

        public void handleMessage(Message message) {
            VideoControllerView videoControllerView = this.mView.get();
            if (videoControllerView != null && videoControllerView.mPlayer != null) {
                int i = message.what;
                if (i == 1) {
                    videoControllerView.hide();
                } else if (i == 2) {
                    int progress = videoControllerView.setProgress();
                    if (!videoControllerView.mDragging && videoControllerView.mShowing && videoControllerView.mPlayer.isPlaying()) {
                        sendMessageDelayed(obtainMessage(2), (long) (1000 - (progress % 1000)));
                    }
                }
            }
        }
    }
}