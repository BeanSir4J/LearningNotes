var app = getApp()
Page({

  onLoad: function () {
    var that = this;
    // 获取openid
    var user = wx.getStorageSync('user') || {};
    if (!user.openid || (user.expires_in || Date.now()) < (Date.now() + 600)) {//不要在30天后才更换openid-尽量提前10分钟更新
      wx.login({
        success: function (res) {
        
          // success
          var d = app.globalData.wxData;//这里存储了appid、secret、token串
          console.log(d);
          var l = 'https://api.weixin.qq.com/sns/jscode2session?appid=' + d.appid + '&secret=' + d.secret + '&js_code=' + res.code + '&grant_type=authorization_code';
          wx.request({
            url: l,
            data: {},
            method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
            // header: {}, // 设置请求的 header
            success: function (res) {
              console.log(res);
              var obj = {};
              obj.openid = res.data.openid;
              obj.expires_in = Date.now() + res.data.expires_in;

              wx.setStorageSync('user', obj);//存储openid
            }
          });
        }
      });
    } else {
      console.log(user);
    }

  },
  orderSign: function (e) {
    var fId = e.detail.formId;
    console.log(fId);
    var fObj = e.detail.value;
    var l = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=' +
"12_KqJCJXmhNEKwJ6sdSim9H0j4zaMPqR5GXqPOWCyAMfEpcIROe0T_gUj1QT3tFLlndW6_IJFmiyEexaNvGwrZU_HrTtOaRNEMVJ5-EWdO71kBCjnYUulh24w3L2DriLRZ7TXpsFW2RdjCKzF4LPVfAEAUYE";
    //app.globalData.wxData.token;
    var d = {
      touser: wx.getStorageSync('user').openid,
      template_id: 'Wh7yIjQgn2JpxRdo0NLN2A-OMBKnel7QEJ8BZ302FAY',//这个是1、申请的模板消息id，
      page: "zh_vip/pages/my/my",
      form_id: e.detail.formId,
      value: {//测试完发现竟然value或者data都能成功收到模板消息发送成功通知，是bug还是故意？？【鄙视、鄙视、鄙视...】 下面的keyword*是你1、设置的模板消息的关键词变量

        "keyword1": {
          "color": "#173177",
          "value": "小牛花"
        },
        "keyword2": {
          "value": "2015年01月05日 12:30"
        },
        "keyword3": {
          "value": "粤海喜来登酒店"
        },
        "keyword4": {
          "value": "广州市天河区天河路208号"
        }
      },
      color: '#ccc',
      emphasis_keyword: 'keyword1.DATA'
    }
    wx.request({
      url: l,
      data: d,
      method: 'POST',
      success: function (res) {
        console.log("push msg");
        console.log(res);
      },
      fail: function (err) {
        // fail
        console.log("push err")
        console.log(err);
      }
    });
  }
})