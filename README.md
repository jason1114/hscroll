# hscroll
An Android ListView that support horizontal slide

## Demo

<img src="https://raw.githubusercontent.com/jason1114/hscroll/master/app/demo.gif">

You can also download the debug demo apk [here](https://raw.githubusercontent.com/jason1114/hscroll/master/app/app-debug.apk)

## How to use

+ Extend the *HScrollAdapter* first
```java
  class MailAdapter extends HScrollAdapter {

        public MailAdapter(Context context) {
            super(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public int getLayout(int type) {
             switch (type) {
                 case TYPE_LEFT:
                     return R.layout.item_left;
                 case TYPE_RIGHT:
                     return R.layout.item_right;
                 case TYPE_CENTER:
                     return R.layout.item;
                 default:
                     throw new IllegalArgumentException();
             }
        }

        @Override
        public void renderView(int position, View left, View center, View right) {
            // getView is set to final, this method is a replacement
        }

        @Override
        public void onState(int position, int state, View left, View center, View right) {
            // Callback when entering one state
            switch (state) {
                case STATE_LEFT_2:
                    // do something ...
                    break;
                case STATE_LEFT_1:
                    // do something ...
                    break;
                case STATE_NORMAL:
                    // do something ...
                    break;
                case STATE_RIGHT_1:
                    // do something ...
                    break;
                case STATE_RIGHT_2:
                    // do something ...
                    break;
            }
        }

        @Override
        public void onStateReleased(int position, int state, View left, View center, View right) {
            // Callback when user's touch is released from one state
            switch (state) {
                 case STATE_LEFT_2:
                     // do something ...
                     break;
                 case STATE_LEFT_1:
                     // do something ...
                     break;
                 case STATE_NORMAL:
                     // do something ...
                     break;
                 case STATE_RIGHT_1:
                     // do something ...
                     break;
                 case STATE_RIGHT_2:
                     // do something ...
                     break;
            }
        }
    }
```
+ Set the adapter to your listview then
```java
list.setAdapter(new MailAdapter(this));
```

+ Enjoy :)

## Contact me

if you have a better idea or way on this project, please let me know, thanks:)

znlswd#gmail.com

## License
```

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

