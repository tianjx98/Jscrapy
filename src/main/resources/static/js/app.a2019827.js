(function(e){function t(t){for(var n,o,l=t[0],i=t[1],c=t[2],d=0,p=[];d<l.length;d++)o=l[d],Object.prototype.hasOwnProperty.call(r,o)&&r[o]&&p.push(r[o][0]),r[o]=0;for(n in i)Object.prototype.hasOwnProperty.call(i,n)&&(e[n]=i[n]);u&&u(t);while(p.length)p.shift()();return s.push.apply(s,c||[]),a()}function a(){for(var e,t=0;t<s.length;t++){for(var a=s[t],n=!0,l=1;l<a.length;l++){var i=a[l];0!==r[i]&&(n=!1)}n&&(s.splice(t--,1),e=o(o.s=a[0]))}return e}var n={},r={app:0},s=[];function o(t){if(n[t])return n[t].exports;var a=n[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,o),a.l=!0,a.exports}o.m=e,o.c=n,o.d=function(e,t,a){o.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},o.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},o.t=function(e,t){if(1&t&&(e=o(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(o.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var n in e)o.d(a,n,function(t){return e[t]}.bind(null,n));return a},o.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return o.d(t,"a",t),t},o.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},o.p="/";var l=window["webpackJsonp"]=window["webpackJsonp"]||[],i=l.push.bind(l);l.push=t,l=l.slice();for(var c=0;c<l.length;c++)t(l[c]);var u=i;s.push([0,"chunk-vendors"]),a()})({0:function(e,t,a){e.exports=a("56d7")},"07b0":function(e,t,a){},"0fc2":function(e,t,a){"use strict";var n=a("07b0"),r=a.n(n);r.a},"56d7":function(e,t,a){"use strict";a.r(t);a("e260"),a("e6cf"),a("cca6"),a("a79d");var n=a("2b0e"),r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"app"}},[a("router-view")],1)},s=[],o={name:"app"},l=o,i=a("2877"),c=Object(i["a"])(l,r,s,!1,null,null,null),u=c.exports,d=a("8c4f"),p=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-container",[a("el-header",[a("span",[a("i",{staticClass:"iconfont iconall"})]),a("h2",[e._v("Jscrapy Admin")]),a("span")]),a("el-container",[a("el-aside",{attrs:{width:e.asideWidth[e.isCollapse?0:1]+"px"}},[a("div",{staticClass:"toggle-button",on:{click:function(t){e.isCollapse=!e.isCollapse}}},[e._v("|||")]),a("el-menu",{attrs:{collapse:e.isCollapse,"collapse-transition":!1,router:"","default-active":e.navState,"background-color":"#333744","text-color":"#fff","active-text-color":"#409eff"}},[e._l(e.getMenuList(!0),(function(t){return a("el-menu-item",{key:t.id,attrs:{index:t.path},on:{click:function(a){return e.saveNavState(t.path)}}},[[a("i",{staticClass:"el-icon-menu"}),a("span",[e._v(e._s(t.name))])]],2)})),e._l(e.getMenuList(!1),(function(t){return a("el-submenu",{key:t.id,attrs:{index:t.path}},[a("template",{slot:"title"},[a("i",{staticClass:"el-icon-setting"}),a("span",[e._v(e._s(t.name))])]),e._l(t.children,(function(t){return a("el-menu-item",{key:t.id,attrs:{index:t.path},on:{click:function(a){return e.saveNavState(t.path)}}},[a("template",{slot:"title"},[a("span",[e._v(e._s(t.name))])])],2)}))],2)}))],2)],1),a("el-main",[a("router-view")],1)],1)],1)},f=[],h=(a("4de4"),a("96cf"),a("1da1")),v={name:"home",data:function(){return{menuList:[{id:1,name:"界面",path:"/dashboard",children:[]},{id:2,name:"配置文件",path:"/config",children:[{id:21,name:"默认请求头以及代理",path:"/defaultHeadersAndProxy",children:[]},{id:22,name:"爬虫以及中间件",path:"/spidersAndMiddlewares",children:[]},{id:23,name:"其他配置",path:"/othersSetting",children:[]}]}],isCollapse:!1,asideWidth:[64,200],navState:""}},methods:{saveNavState:function(e){this.navState=e,window.sessionStorage.setItem("navState",e)},getMenuList:function(e){return this.menuList.filter((function(t){return 0===t.children.length===e}))}},computed:{},created:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:e.navState=window.sessionStorage.getItem("navState");case 1:case"end":return t.stop()}}),t)})))()}},m=v,g=(a("0fc2"),Object(i["a"])(m,p,f,!1,null,"ec09d334",null)),b=g.exports,y=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-card",[a("div",{attrs:{slot:"header"},slot:"header"},[a("span",[e._v(e._s(e.config[0].name))]),a("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:e.config[0].description,placement:"top"}},[a("i",{staticClass:"el-icon-info"})]),a("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(t){e.dialogFormVisible=!0}}},[e._v("添加")])],1),e._l(e.config[0].value,(function(t,n){return a("el-row",{key:n,attrs:{gutter:15}},[a("el-col",{attrs:{span:5}},[a("el-input",{model:{value:t.key,callback:function(a){e.$set(t,"key",a)},expression:"obj.key"}})],1),a("el-col",{attrs:{span:1}},[e._v(":")]),a("el-col",{attrs:{span:17}},[a("el-input",{model:{value:t.value,callback:function(a){e.$set(t,"value",a)},expression:"obj.value"}})],1),a("el-col",{attrs:{span:1}},[a("i",{staticClass:"el-icon-error",staticStyle:{color:"red",cursor:"pointer"},on:{click:function(t){return e.config[0].value.splice(n,1)}}})])],1)}))],2),a("el-card",[a("div",{staticStyle:{display:"flex","justify-content":"space-between"},attrs:{slot:"header"},slot:"header"},[a("span",[e._v(e._s(e.config[1].name)+" "),a("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:e.config[1].description,placement:"top"}},[a("i",{staticClass:"el-icon-info"})])],1),a("el-switch",{attrs:{"active-color":"#13ce66","inactive-color":"gray"},model:{value:e.config[1].value[0].value,callback:function(t){e.$set(e.config[1].value[0],"value",t)},expression:"config[1].value[0].value"}})],1),a("el-row",{attrs:{gutter:15}},[a("el-col",{staticClass:"keyClass",attrs:{span:2}},[a("div",[e._v(e._s(e.config[1].value[1].key))])]),a("el-col",{attrs:{span:1}},[e._v(":")]),a("el-col",{attrs:{span:21}},[a("el-input",{model:{value:e.config[1].value[1].value,callback:function(t){e.$set(e.config[1].value[1],"value",t)},expression:"config[1].value[1].value"}})],1)],1),a("el-row",{attrs:{gutter:15}},[a("el-col",{staticClass:"keyClass",attrs:{span:2}},[a("div",[e._v(e._s(e.config[1].value[2].key)+":")])]),a("el-col",{attrs:{span:1}},[e._v(":")]),a("el-col",{attrs:{span:21}},[a("el-input",{model:{value:e.config[1].value[2].value,callback:function(t){e.$set(e.config[1].value[2],"value",e._n(t))},expression:"config[1].value[2].value"}})],1)],1)],1),a("el-dialog",{attrs:{title:"添加默认请求头",visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("el-row",{attrs:{gutter:15}},[a("el-col",{staticClass:"keyClass",attrs:{span:3}},[a("div",[e._v("Header :")])]),a("el-col",{attrs:{span:20}},[a("el-input",{model:{value:e.newHeader,callback:function(t){e.newHeader=t},expression:"newHeader"}})],1)],1),a("el-row",{attrs:{gutter:15}},[a("el-col",{staticClass:"keyClass",attrs:{span:3}},[a("div",[e._v("Value :")])]),a("el-col",{attrs:{span:20}},[a("el-input",{model:{value:e.newValue,callback:function(t){e.newValue=t},expression:"newValue"}})],1)],1),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary"},on:{click:e.addDefaultHeader}},[e._v("确 定")])],1)],1)],1)},w=[],k={name:"defaultHeadersAndProxy",created:function(){this.getConfig()},data:function(){return{config:[{name:"默认请求头",description:"每次发送请求时都会添加这些请求头",key:"defaultHeaders",value:[{name:"",description:"",key:"Accept",value:"*/*"},{name:"",description:"",key:"Accept-Language",value:"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"}]},{name:"代理",description:"每次发送请求都会经过代理",key:"proxy",value:[{name:"",description:"",key:"enable",value:!1},{name:"",description:"",key:"host",value:""},{name:"",description:"",key:"port",value:8888}]}],dialogFormVisible:!1,newHeader:"",newValue:""}},methods:{getConfig:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/config/defaultHeadersAndProxy");case 2:a=t.sent,n=a.data,1===n.length&&n.push({name:"代理",description:"每次发送请求都会经过代理",key:"proxy",value:[{name:"",description:"",key:"host",value:""},{name:"",description:"",key:"port",value:""}]}),e.config=n;case 6:case"end":return t.stop()}}),t)})))()},deleteKeyValue:function(e,t){n["default"].delete(e,t)},setKeyValue:function(e,t,a){n["default"].set(e,t,a)},addDefaultHeader:function(){n["default"].set(this.config.value[0].data,this.newHeader,this.newValue),this.dialogFormVisible=!1,this.newHeader="",this.newValue=""}},computed:{},destroyed:function(){this.$http.put("/config/update",{config:this.config})}},_=k,x=(a("fc0d"),Object(i["a"])(_,y,w,!1,null,"0a255867",null)),C=x.exports,R=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",e._l(e.config,(function(t,n){return a("el-card",{key:"outside"+n},[a("div",{attrs:{slot:"header"},slot:"header"},[a("span",[e._v(e._s(t.name))]),a("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:t.description,placement:"top"}},[a("i",{staticClass:"el-icon-info"})]),a("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(e){return t.value.push({name:"",description:"",key:"",value:50})}}},[e._v("添加")])],1),a("draggable",{attrs:{draggable:".configRow"}},e._l(t.value,(function(n,r){return a("el-row",{key:"inside"+r,staticClass:"configRow",attrs:{gutter:15}},[a("el-col",{attrs:{span:20}},[a("el-input",{model:{value:n.key,callback:function(t){e.$set(n,"key",t)},expression:"obj.key"}})],1),a("el-col",{attrs:{span:1}},[e._v(":")]),a("el-col",{staticClass:"keyClass",attrs:{span:2}},[a("el-input",{model:{value:n.value,callback:function(t){e.$set(n,"value",e._n(t))},expression:"obj.value"}})],1),a("el-col",{attrs:{span:1}},[a("i",{staticClass:"el-icon-error",staticStyle:{color:"red",cursor:"pointer"},on:{click:function(e){return t.value.splice(r,1)}}})])],1)})),1)],1)})),1)},S=[],j=a("310e"),$=a.n(j),I={name:"spidersAndMiddlewares",components:{draggable:$.a},created:function(){this.getConfig()},data:function(){return{config:[{name:"爬虫对象",description:"系统启动时会加载这些爬虫对象",key:"spiders",value:[{name:"",description:"",key:"aca",value:1}]},{name:"爬虫中间件",description:"处理进入爬虫的响应和爬虫产生的请求, 数值越小优先级越高",key:"spiderMiddlewares",value:[{name:"",description:"",key:"me.tianjx98.Jscrapy.middleware.spider.impl.DepthSpiderMiddleware",value:1},{name:"",description:"",key:"me.tianjx98.Jscrapy.middleware.spider.impl.AllowedDomainSpiderMiddleware",value:2}]},{name:"持久化Pipelines",description:"处理爬虫产生的item，数字越小优先级越高",key:"itemPipelines",value:[{name:"",description:"",key:"test.scraper.novel.NovelPipeline",value:1}]}]}},methods:{getConfig:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/config/spidersAndMiddlewares");case 2:a=t.sent,n=a.data,e.config=n;case 5:case"end":return t.stop()}}),t)})))()},deleteKeyValue:function(e,t){n["default"].delete(e,t)}},mounted:function(){},destroyed:function(){this.$http.put("/config/update",{config:this.config})}},O=I,q=(a("88d8"),Object(i["a"])(O,R,S,!1,null,"1c612939",null)),V=q.exports,D=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-row",{staticClass:"outsideRow",attrs:{gutter:20}},[a("el-col",[a("el-card",[a("div",{attrs:{slot:"header"},slot:"header"},[a("span",[e._v("其他配置")])]),e._l(e.config,(function(t,n){return a("el-row",{key:n,attrs:{gutter:15}},[a("el-col",{staticClass:"keyClass",attrs:{span:12}},[a("div",[e._v(e._s(t.name)+":")])]),a("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:t.description,placement:"top"}},[a("i",{staticClass:"el-icon-info"})]),a("el-col",{attrs:{span:6}},[a("el-input",{model:{value:t.value,callback:function(a){e.$set(t,"value",e._n(a))},expression:"obj.value"}})],1)],1)}))],2)],1),a("el-col",{staticStyle:{"margin-top":"0"}},[a("el-card",{staticStyle:{height:"100%"}},[a("div",{attrs:{slot:"header"},slot:"header"},[a("span",[e._v(e._s(e.scheduler.name))])]),a("el-row",{attrs:{gutter:15}},[a("el-col",{staticClass:"keyClass",attrs:{span:5}},[a("div",[e._v(e._s(e.scheduler.value[0].name)+":")])]),a("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:e.scheduler.value[0].description,placement:"top"}},[a("i",{staticClass:"el-icon-info"})]),a("el-col",{attrs:{span:18}},[a("el-input",{model:{value:e.scheduler.value[0].value,callback:function(t){e.$set(e.scheduler.value[0],"value",t)},expression:"scheduler.value[0].value"}})],1)],1),a("el-row",{attrs:{gutter:15}},[a("el-col",{staticClass:"keyClass",attrs:{span:5}},[a("div",[e._v(e._s(e.scheduler.value[1].name)+":")])]),a("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:e.scheduler.value[1].description,placement:"top"}},[a("i",{staticClass:"el-icon-info"})]),a("el-col",{attrs:{span:18}},[a("el-radio",{attrs:{label:!0},model:{value:e.scheduler.value[1].value,callback:function(t){e.$set(e.scheduler.value[1],"value",t)},expression:"scheduler.value[1].value"}},[e._v("广度优先")]),a("el-radio",{attrs:{label:!1},model:{value:e.scheduler.value[1].value,callback:function(t){e.$set(e.scheduler.value[1],"value",t)},expression:"scheduler.value[1].value"}},[e._v("深度优先")])],1)],1)],1)],1)],1)],1)},P=[],N={name:"othersSetting",created:function(){this.getConfig()},data:function(){return{config:[{name:"最大线程数",description:"同时工作的线程数量",key:"maxThreadNum",value:4},{name:"请求超时时间",description:"如果发出请求后超出此时间还未收到响应就取消此次请求,单位为s",key:"connectionTimeout",value:10},{name:"并发请求的最大值",description:"同一时间允许发出请求的最大数量,默认最多可以同时发出16个请求",key:"concurrentRequests",value:16},{name:"每一个域名下的并发请求最大值",description:"同一域名下同一时间允许发出请求的最大数量,默认最多可以同时发出8个请求",key:"concurrentRequestsPerDomain",value:8},{name:"随机下载延迟",description:"随机下载延迟, 对于同一个域名下的请求, 两次请求之间会产生一个0-randomDownloadDelay(s)的延迟",key:"randomDownloadDelay",value:0}],scheduler:{name:"调度器配置",description:"",key:"scheduler",value:[{name:"URL去重类",description:"用于实现调度器中的去重算法,默认使用 布隆过滤器",key:"defaultDupFilter",value:"me.tianjx98.Jscrapy.duplicatefilter.impl.BloomDuplicateFilter"},{name:"爬取规则",description:"默认为（true）广度优先爬取，设置为false则是深度优先爬取",key:"bfs",value:!0}]},dialogFormVisible:!1,newHeader:"",newValue:""}},methods:{getConfig:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){var a,n,r,s;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/config/others");case 2:return a=t.sent,n=a.data,e.config=n,t.next=7,e.$http.get("/config/scheduler");case 7:r=t.sent,s=r.data,e.scheduler=s;case 10:case"end":return t.stop()}}),t)})))()},deleteKeyValue:function(e,t){n["default"].delete(e,t)},setKeyValue:function(e,t,a){n["default"].set(e,t,a)},addDefaultHeader:function(){n["default"].set(this.config[0].data,this.newHeader,this.newValue),this.dialogFormVisible=!1,this.newHeader="",this.newValue=""}},destroyed:function(){this.$http.put("/config/update",{config:this.config}),this.$http.put("/config/update",{config:this.scheduler})}},E=N,H=(a("8850"),Object(i["a"])(E,D,P,!1,null,"ee84d9b6",null)),z=H.exports,M=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-card",[a("div",{attrs:{slot:"header"},slot:"header"},[a("span",[e._v("引擎")]),a("span",{staticStyle:{"margin-left":"20px","margin-right":"20px"}},[e._v(e._s(e.engineStatus))]),a("el-button",{attrs:{type:"primary"},on:{click:e.startEngine}},[e._v("启动")]),a("el-button",{attrs:{type:"warning",disabled:e.closed},on:{click:e.pauseEngine}},[e._v("暂停")]),a("el-button",{attrs:{type:"danger",disabled:e.closed},on:{click:e.closeEngine}},[e._v("关闭")])],1),a("el-row",{attrs:{gutter:15,type:"flex"}},[a("el-col",{attrs:{span:12}},[a("el-card",[a("div",{staticStyle:{display:"flex","justify-content":"space-between"},attrs:{slot:"header"},slot:"header"},[a("span",[e._v("Scheduler")]),a("span",[e._v("总请求数: "+e._s(e.schedulerInfo.totalRequestNum))]),a("span",[e._v("当前请求数: "+e._s(e.schedulerInfo.currentRequestNum))]),a("span",[e._v("重复请求: "+e._s(e.schedulerInfo.repeatRequestNum))])]),a("div",{staticClass:"box"},[a("div",{staticStyle:{overflow:"auto",height:"100%"}},[a("el-tree",{attrs:{data:e.schedulerInfo.data,props:e.defaultProps}})],1)]),a("el-pagination",{attrs:{"current-page":e.page,"page-sizes":[5,10,15],small:!0,"page-size":e.pageSize,layout:"sizes, prev, pager, next, jumper",total:e.schedulerInfo.currentRequestNum},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentPageChange}})],1)],1),a("el-col",{attrs:{span:12}},[a("el-card",[a("div",{staticStyle:{display:"flex","justify-content":"space-between"},attrs:{slot:"header"},slot:"header"},[a("span",[e._v("Downloader")]),a("span",[e._v("已发送请求数: "+e._s(e.downloaderInfo.totalDownloadRequests))]),a("span",[e._v("当前请求数: "+e._s(e.downloaderInfo.downloadingRequests))])]),a("div",{staticClass:"box"},[a("el-tree",{attrs:{data:e.downloaderInfo.data,props:e.defaultProps}})],1)])],1)],1)],1)],1)},A=[],L=(a("99af"),a("a434"),a("b85c")),F={name:"dashboard",data:function(){return{closed:!0,paused:!1,page:1,pageSize:5,busy:!1,schedulerInfo:{totalRequestNum:0,repeatRequestNum:0,currentRequestNum:0,data:[]},downloaderInfo:{downloadingRequests:0,totalDownloadRequests:0,data:[]},defaultProps:{label:"label",children:"children"}}},created:function(){this.loadEngineInfo(),setInterval(this.loadDownloaderInfo,3e3),setInterval(this.loadSchedulerInfo,3e3)},methods:{loadEngineInfo:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/engine/info");case 2:a=t.sent,n=a.data,e.closed=n.closed,e.paused=n.paused;case 6:case"end":return t.stop()}}),t)})))()},loadSchedulerInfo:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(!e.closed){t.next=2;break}return t.abrupt("return");case 2:return t.next=4,e.$http.get("/engine/scheduler/".concat(e.page,"/").concat(e.pageSize));case 4:a=t.sent,n=a.data,e.schedulerInfo.totalRequestNum=n.totalRequestNum,e.schedulerInfo.repeatRequestNum=n.repeatRequestNum,e.schedulerInfo.currentRequestNum=n.currentRequestNum,e.schedulerInfo.data.splice(0,e.schedulerInfo.data.length),e.schedulerInfo.data.push.apply(e.schedulerInfo.data,e.formatList(n.data));case 11:case"end":return t.stop()}}),t)})))()},loadDownloaderInfo:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){var a,n,r,s,o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(!e.closed){t.next=2;break}return t.abrupt("return");case 2:return t.next=4,e.$http.get("/engine/downloader");case 4:for(s in a=t.sent,n=a.data,e.downloaderInfo.downloadingRequests=n.downloadingRequests,e.downloaderInfo.totalDownloadRequests=n.totalDownloadRequests,r=0,n.data)o=e.formatList(n.data[s]),e.downloaderInfo.data.length===r&&e.downloaderInfo.data.push({label:"",children:[]}),e.downloaderInfo.data[r].label=s,e.downloaderInfo.data[r].children.splice(0,e.downloaderInfo.data[r].children.length),e.downloaderInfo.data[r].children.push.apply(e.downloaderInfo.data[r].children,o),r++;case 10:case"end":return t.stop()}}),t)})))()},formatList:function(e){var t=[];if(null===e)return t;var a,n=Object(L["a"])(e);try{for(n.s();!(a=n.n()).done;){var r=a.value;t.push(this.formatObj(r))}}catch(s){n.e(s)}finally{n.f()}return t},formatObj:function(e){var t={children:[]};for(var a in e)"url"===a?t.label=e[a]:t.children.push(this.convertKeyValue(a,e[a]));return t},convertKeyValue:function(e,t){var a={children:[]};if(t instanceof Array){a.label=e;var n,r=Object(L["a"])(t);try{for(r.s();!(n=r.n()).done;){var s=n.value;a.children.push({label:s})}}catch(l){r.e(l)}finally{r.f()}return a}if(t instanceof Object){var o=this.formatObj(t);return o.label=e,o}return a.label=e+": "+t,a},startEngine:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(e.closed){t.next=4;break}e.$messageBox.confirm("是否重启引擎?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(Object(h["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/engine/control/start");case 2:e.loadEngineInfo(),e.$message({type:"success",message:"重启引擎成功!"});case 4:case"end":return t.stop()}}),t)})))).catch((function(){e.$message({type:"info",message:"已取消重启"})})),t.next=8;break;case 4:return t.next=6,e.$http.get("/engine/control/start");case 6:e.$message.success("爬虫引擎启动成功!"),e.loadEngineInfo();case 8:case"end":return t.stop()}}),t)})))()},pauseEngine:function(){var e=this;return Object(h["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.paused=!e.paused,t.next=3,e.$http.get("/engine/control/pause");case 3:e.loadSchedulerInfo(),e.loadDownloaderInfo(),e.$message.success("引擎已"+(e.paused?"暂停":"启动")+"!");case 6:case"end":return t.stop()}}),t)})))()},closeEngine:function(){var e=this;this.$messageBox.confirm("是否关闭引擎?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(Object(h["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/engine/control/close");case 2:return t.next=4,e.loadEngineInfo();case 4:e.$message({type:"success",message:"引擎关闭成功!"});case 5:case"end":return t.stop()}}),t)})))).catch((function(){e.$message({type:"info",message:"已取消关闭"})}))},handleSizeChange:function(e){this.pageSize=e,this.loadSchedulerInfo()},handleCurrentPageChange:function(e){this.page=e,this.loadSchedulerInfo()}},computed:{engineStatus:function(){var e="";return e=this.closed?"未启动...":this.paused?"暂停中...":"运行中...",e}}},T=F,B=(a("aba2"),Object(i["a"])(T,M,A,!1,null,"1ad06cb4",null)),K=B.exports;n["default"].use(d["a"]);var J=[{path:"/",component:b,children:[{path:"dashboard",component:K},{path:"/defaultHeadersAndProxy",component:C},{path:"/spidersAndMiddlewares",component:V},{path:"/othersSetting",component:z}]}],U=new d["a"]({routes:J}),W=U,G=(a("4160"),a("b0c0"),a("159b"),a("b5d8"),a("450d"),a("f494")),Q=a.n(G),X=(a("9e1f"),a("6ed5")),Y=a.n(X),Z=(a("0fb7"),a("f529")),ee=a.n(Z),te=(a("672e"),a("101e")),ae=a.n(te),ne=(a("5e32"),a("6721")),re=a.n(ne),se=(a("e960"),a("b35b")),oe=a.n(se),le=(a("1760"),a("9883")),ie=a.n(le),ce=(a("0c67"),a("299c")),ue=a.n(ce),de=(a("a7cc"),a("df33")),pe=a.n(de),fe=(a("10cb"),a("f3ad")),he=a.n(fe),ve=(a("b8e0"),a("a4c4")),me=a.n(ve),ge=(a("f4f9"),a("c2cc")),be=a.n(ge),ye=(a("7a0f"),a("0f6c")),we=a.n(ye),ke=(a("8bd8"),a("4cb2")),_e=a.n(ke),xe=(a("ce18"),a("f58e")),Ce=a.n(xe),Re=(a("4ca3"),a("443e")),Se=a.n(Re),je=(a("de31"),a("c69e")),$e=a.n(je),Ie=(a("a769"),a("5cc3")),Oe=a.n(Ie),qe=(a("a673"),a("7b31")),Ve=a.n(qe),De=(a("adec"),a("3d2d")),Pe=a.n(De),Ne=(a("1951"),a("eedf")),Ee=a.n(Ne),He=[Ee.a,Pe.a,Ve.a,Oe.a,$e.a,Se.a,Ce.a,_e.a,we.a,be.a,me.a,he.a,pe.a,ue.a,ie.a,oe.a,re.a,ae.a,ee.a,Y.a,Q.a];He.forEach((function(e){n["default"].component(e.name,e)})),n["default"].prototype.$message=ee.a,n["default"].prototype.$messageBox=Y.a;a("aede");var ze=a("bc3a"),Me=a.n(ze);Me.a.defaults.baseURL="http://localhost:8080",Me.a.defaults.timeout=1e3,n["default"].prototype.$http=Me.a,n["default"].config.productionTip=!1,new n["default"]({router:W,render:function(e){return e(u)}}).$mount("#app")},8850:function(e,t,a){"use strict";var n=a("d571"),r=a.n(n);r.a},"88d8":function(e,t,a){"use strict";var n=a("a11c"),r=a.n(n);r.a},9620:function(e,t,a){},a11c:function(e,t,a){},aba2:function(e,t,a){"use strict";var n=a("9620"),r=a.n(n);r.a},aede:function(e,t,a){},c22d:function(e,t,a){},d571:function(e,t,a){},fc0d:function(e,t,a){"use strict";var n=a("c22d"),r=a.n(n);r.a}});
//# sourceMappingURL=app.a2019827.js.map