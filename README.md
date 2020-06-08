# 淘菜
一款基于LeanCloud的电商类App，实现了基本的首页Banner轮播，商品展示分类展示，购物车列表生成，订单生成以及快递信息跟踪等功能。

## 技术支持
* 该App所有数据均来自LeanCloud后端云实现
* 基于LeanCloud，实现了用户注册的短信验证功能
* 首页轮播图使用GitHub开源框架[Banner](https://github.com/youth5201314/banner)
* 使用Glide加载图片
* 使用zxingLibrary实现了二维码的扫描
* 头像使用CircleImgaeView
* 集成了聚合数据快递订单接口

## 用户端功能
* 用户可以通过手机号码验证短信进行注册，成功后可登录进入App
* 通过首页可以浏览轮播图和热门商品
* 通过商品详情页，用户可以将商品收藏或加入购物车
* 用户在购物车页面可以对购物车中的商品进行删除，修改数量等操作
* 用户完成商品选购后，可以选择收货地址，进行支付下单操作
* 用户在个人中心可以对收货地址，订单，收藏商品等进行查看管理
* 对于已完成的订单，用户可以查看其订单详情，包括物流信息，对订单进行评价等操作

## 商户端功能
* 商家可以通过username：admin  password：admin进入商户端App
* 进入商户App后，可以在首页对商品进行管理，如添加，删除，修改等操作。
* 在订单模块，商家可以查看所有订单内容，并对订单进行发货操作。

## 用户端部分功能展示
![](https://github.com/AndroidYiku/TaoCai/blob/master/images/user_home.png) 
![](https://github.com/AndroidYiku/TaoCai/blob/master/images/user_shopcart.png)
![](https://github.com/AndroidYiku/TaoCai/blob/master/images/user_order_pay.png)
![](https://github.com/AndroidYiku/TaoCai/blob/master/images/user_mine.png)

## 商户端部分功能展示
![](https://github.com/AndroidYiku/TaoCai/blob/master/images/shop_goods_update.png) 
![](https://github.com/AndroidYiku/TaoCai/blob/master/images/shop_order.png)
![](https://github.com/AndroidYiku/TaoCai/blob/master/images/shop_send_goods.png)

## 提示
* 该项目为本人毕设项目，分享出来用于同学们学习讨论交流，目前已暂停维护。如有任何问题，可通过邮箱906275807@qq.com联系我，或者为该项目提出issue。
* 由于出现过有些不法分子拿此项目去骗取其他同学财物，所以目前关闭了该项目的后台数据服务。若有同学需要我毕设的其他材料或者LeanCloud数据库信息，可以联系我获取。
