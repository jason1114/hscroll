# hscroll
An Android ListView that support horizontal slide

## Demo

<img src="https://raw.githubusercontent.com/jason1114/hscroll/master/app/demo.gif">

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
        public int getLeftLayout() {
            return R.layout.item_left;
        }

        @Override
        public int getCenterLayout() {
            return R.layout.item;
        }

        @Override
        public int getRightLayout() {
            return R.layout.item_right;
        }

        @Override
        public void renderView(int position, View left, View center, View right) {
            // getView is set to final, this method is a replacement
        }

        @Override
        public void onStateLeft2(int position, View left, View center, View right) {
            // As the demo shows, the red 'delete' action state is 'StateLeft2'
        }

        @Override
        public void onStateLeft1(int position, View left, View center, View right) {
            // As the demo shows, the green 'check' action state is 'StateLeft1'
        }

        @Override
        public void onStateNormal(int position, View left, View center, View right) {
            // normal state
        }

        @Override
        public void onStateRight1(int position, View left, View center, View right) {
            // As the demo shows, the light blue 'message' action state is 'StateRight1'
        }

        @Override
        public void onStateRight2(int position, View left, View center, View right) {
            // As the demo shows, the orange 'delete' action state is 'StateLeft2'
        }

        @Override
        public void onStateLeft1Released(int position, View left, View center, View right) {
            // called when user's touch is released from 'StateLeft1'
        }

        @Override
        public void onStateLeft2Released(int position, View left, View center, View right) {
            // called when user's touch is released from 'StateLeft2'
        }

        @Override
        public void onStateRight1Released(int position, View left, View center, View right) {
            // called when user's touch is released from 'StateRight1'
        }

        @Override
        public void onStateRight2Released(int position, View left, View center, View right) {
            // called when user's touch is released from 'StateRight2'
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

