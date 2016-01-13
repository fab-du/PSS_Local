"use strict";angular.module("cryptClientApp",["ngAnimate","ngAria","ngCookies","ngMessages","ngResource","ngRoute","ngMaterial","ui.router","ngMockE2E","ui.grid","ui.grid.selection","ngSanitize"]).config(["$routeProvider","$resourceProvider","$stateProvider",function(a,b,c){c.state("login",{templateUrl:"/views/session/login.html"}).state("logout",{}).state("register",{templateUrl:"/views/session/register.html"}),c.state("users",{url:"/users",controller:"UsersController",templateUrl:"/views/users.html"}).state("users.groups",{url:"/users_grp_admin",controller:"UserGroupController",templateUrl:"/views/groups/mygroups.html"}).state("users.userId",{url:"/:userId"}).state("users.userId.groups",{url:"/groups"}).state("users.userId.friends",{url:"/friends"}).state("users.userId.documents",{url:"/documents"}),c.state("groups",{url:"/groups",controller:"GroupsController",templateUrl:"/views/groups.html"}).state("groups.admin",{url:"/groups_admin",templateUrl:"/views/groups/my_groups.html"}).state("groups.groupId",{url:"/:groupId"}).state("groups.groupId.documents",{url:"/documents"}).state("groups.groupId.users",{url:"/users"}),c.state("documents",{url:"/documents",templateUrl:"/views/documents.html"}).state("documents.documentId",{url:"/:documentId"}),c.state("friends",{url:"/friends",templateUrl:"/views/friends.html"}).state("friends.friendId",{url:"/:friendId"}).state("friends.friendId.groups",{url:"/groups"}).state("friends.friendId.friends",{url:"/friends"}),a.otherwise({redirectTo:"/"})}]),angular.module("cryptClientApp").controller("MainController",["$scope","$state","Storage","$rootScope",function(a,b,c,d){c.set("currentUser",{email:"tux@linux.com",x_token:"xxxx",id:1}),c.set("isLoggedIn",!0),a.tabs={users:{label:"users",contents:["friends","groups","search"]},documents:{label:"documents",contents:["private","groups"]},groups:{label:"groups",contents:["admin","member","search"]},friends:{label:"friends",contents:["find"]}},a.title="Index",a.$watch("title",function(a,b){a!==b&&(b=a)}),a.onTabSelected=function(c){a.title=c,b.go(c)},a.tabContentClick=function(a,c){b.go(a+"."+c)}}]),angular.module("cryptClientApp").controller("AboutCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}),angular.module("cryptClientApp").factory("Rest",["$resource","RouteUsers","RouteDocuments","RouteFriends","RouteGroups",function(a,b,c,d,e){function f(){angular.forEach(h,function(b,c){g[c]=a(b.url,b.defaults,b.actions,b.opts)})}var g={},h={User:b.routes,Group:e.routes,Document:c.routes,Friend:d.routes};return f(),g}]),angular.module("cryptClientApp").factory("Storage",["$cookies","$filter",function(a,b){function c(){var b=new Date((new Date).getTime()+6e5),c=b.toUTCString().toString();d.expires=c,a.put("refresh","",d)}var d={secure:!1},e=function(b){var c=a.get(b);return void 0===c?null:JSON.parse(c)},f=function(d,e){return c(),angular.isString(d)?angular.isObject(e)?(a.put(d,b("json")(e)),!0):angular.isString(e)?(a.put(d,e),!0):void 0:!1},g=function(){return a.getAll()},h=function(a){angular.forEach(a,function(a,b){f(b,a,d)})},i=function(){var b=g();angular.forEach(b,function(b,c){a.remove(c)})},j={get:e,set:f,getAll:g,putAll:h,remove:i};return j}]),angular.module("cryptClientApp").factory("RouteDocuments",function(){var a={url:"/api/documents/:documentId",defaults:{documentId:"@documentId"},actions:{find:{method:"GET"},findOne:{method:"GET"},changeOwner:{method:"POST",params:{verb:"changeOwner"}},shareDocument:{method:"POST",params:{verb:"shareDocument",group:"@group"}},addDocument:{method:"POST",params:{group:"group"},transformRequest:angular.entity,headers:{"Content-Type":void 0}}}};return{routes:a}}),angular.module("cryptClientApp").factory("RouteUsers",function(){var a={url:"/api/users/:userId/:suffix",defaults:{userId:"@userId",suffix:""},actions:{find:{method:"GET",isArray:!0},findOne:{method:"GET"},create:{method:"POST"},validate:{method:"POST",params:{suffix:"validate"}},addFriend:{method:"POST",params:{suffix:"addFriend"}},revoke:{method:"POST",params:{suffix:"revoke"}},addToGroup:{method:"POST",params:{suffix:"addToGroup"}},friends:{method:"GET",params:{suffix:"friends"}},groups:{method:"GET",params:{suffix:"groups"}}},opts:{}};return{routes:a}}),angular.module("cryptClientApp").factory("RouteFriends",function(){var a={url:"/api/friends/:friendId/:suffix",defaults:{friendId:"@friendId",suffix:""},actions:{find:{method:"GET"},findOne:{method:"GET"},addFriend:{method:"POST"},validate:{method:"POST",params:{suffix:"validate"}},revokeFriend:{method:"POST",params:{suffix:"revoke"}},addToGroup:{method:"POST",params:{suffix:"addToGroup",group:"@groupId"}}}};return{routes:a}}),angular.module("cryptClientApp").factory("RouteGroups",function(){var a={url:"/api/groups/:groupId/:suffix",defaults:{groupId:"@groupId",suffix:""},actions:{find:{method:"GET",isArray:!0},findOne:{method:"GET"},create:{method:"POST"},addFriend:{method:"POST"},documents:{method:"GET",params:{suffix:"documents"}},users:{method:"GET",params:{suffix:"users"}}},opts:{}};return{routes:a}}),angular.module("cryptClientApp").controller("UsersController",["$scope","$rootScope","$state","Rest","Storage",function(a,b,c,d,e){function f(){e.get("users")?a.users=e.get("users"):d.User.find().$promise.then(function(b){a.users=b,e.set("users",b)})}f()}]).controller("UserGroupController",["$scope","Storage","Rest",function(a,b,c){console.log("come here"),b.get("my_groups")?a.my_groups=b.get("my_groups"):c.Group.find({gv:b.get("currentUser").id}).$promise.then(function(c){b.set("my_groups",c),a.my_groups=c})}]),angular.module("cryptClientApp").controller("GroupsController",["Rest","Storage","$scope",function(a,b,c){function d(){b.get("groups")?c.groups=b.get("groups"):a.Group.find().$promise.then(function(a){b.set("groups",a),c.groups=a}),b.get("my_groups")?c.my_groups=b.get("my_groups"):a.Group.find({gv:b.get("currentUser").id}).$promise.then(function(a){b.set("my_groups",a),c.my_groups=a})}d()}]),angular.module("cryptClientApp").controller("DocumentsController",function(){}),angular.module("cryptClientApp").controller("FriendsCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}),angular.module("cryptClientApp").run(["$httpBackend",function(a){a.whenGET("/api/users").respond(function(){var a=[{id:2,email:"tux@tux.com",friends:[3,4]},{id:3,email:"linux@linux.com",friends:[2]},{id:4,email:"fabrice@fabrice.com"},{id:5,email:"levinas@levinas.com"}];return[200,a,{}]}),a.whenGET("/api/groups").respond(function(a,b,c){console.log(c),console.log(b);var d=[{id:2,gv:5,name:"levinas_group",users:[5,1]},{id:3,gv:4,name:"fabrice_group",users:[4,1]},{id:4,gv:2,name:"tux_group",users:[2]},{id:5,gv:3,name:"linux_group",users:[3]}];return[200,d,{}]}),a.whenGET(/(\?|\&)([^=]+)\=([^&]+)/).respond(function(a,b,c){console.log(a),console.log(b),console.log(c);var d=[{id:2,gv:5,name:"levinas_group",users:[5,1]},{id:3,gv:4,name:"fabrice_group",users:[4]}];return[200,d,{}]}),a.whenGET(/views\//).passThrough()}]),angular.module("cryptClientApp").filter("filter",function(){return function(a){return"filter filter: "+a}}),angular.module("cryptClientApp").factory("Interceptor",["$q","$location","Storage",function(a,b,c){return{request:function(a){return void 0===a.headers["X-XSRF-TOKEN"]&&(a.headers["X-XSRF-TOKEN"]=c.get("X-XSRF-TOKEN")||""),void 0===a.headers.Authorization&&(a.headers.Authorization="SRP"),void 0===a.headers["WWW-Authenticate"]&&(a.headers["WWW-Authenticate"]="SRP"),void 0===a.headers["hash-algorithm"]&&(a.headers["hash-algorithm"]="SHA256"),void 0===a.headers.realm&&(a.headers.realm="realm"),void 0===a.headers.expires_in&&(a.headers.expires_in=3600),void 0===a.headers.token_type&&(a.headers.token_type="bearer"),void 0===a.headers.access_token&&(a.headers.access_token="xsrh"),void 0===a.headers.client_public_key&&(a.headers.client_public_key="pubkey"),void 0===a.headers.server_public_key&&(a.headers.server_public_key="ddodi"),a},response:function(a){return a},responseError:function(a){},requestError:function(a){}}}]).config(["$httpProvider",function(a){a.interceptors.push("Interceptor")}]),angular.module("cryptClientApp").factory("Auth",["$http","$state","Storage",function(a,b,c){function d(a){c.putAll(a)}function e(){c.remove()}function f(c,d,e){a.post("/session/register",c).success(function(a){b.go("login")}).error(e)}function g(e,f,g){a.post("/session/login/challenge",e).success(function(a,e,f){c.set("prikey",a.prikey);var g=f();d(g),b.go("api")}).error(g)}function h(){return void 0===c.getAll()}var i={},j=c.getAll()||{};return i.logout=function(){e()},i.currentSession=j,i.register=function(a,b,c){var d=0;return angular.forEach(a,function(a,b){"email"===b&&d++,"password"===b&&d++,"passphrase"===b&&d++,"firstname"===b&&d++,"secondname"===b&&d++,"company"===b&&d++}),6===d?f(a,b,c):!1},i.login=function(a,b,c){g(a,b,c)},i.isLoggedIn=function(a){return h(a)},i}]),angular.module("cryptClientApp").directive("tab",function(){return{template:'<style ui-grid-style>{{ ui-style }}</style><div ui-grid=gridOptions class="grid" ui-grid-selection ></div>',restrict:"E",transclude:!0,scope:{data:"="},link:function(a){a.gridOptions={enableSelectAll:!0,enableSelection:!0,enableFiltering:!0,selectionRowHeaderWidth:35,rowHeight:35,data:a.data,showGridFooter:!0},a.myStyle=".grid { border: 1px solid blue }",a.gridOptions.onRegisterApi=function(b){a.gridApi=b,b.selection.on.rowSelectionChanged(a,function(a){console.log(a)})}}}}).directive("accessLevel",["Auth",function(a){return{restrict:"A",link:function(b,c,d){function e(){f&&g&&(a.authorize(g,f)?c.css("display",h):c.css("display","none"))}var f,g,h=c.css("display");b.user=a.user,b.$watch("user",function(a){a.role&&(f=a.role),e()},!0),d.$observe("accessLevel",function(a){a&&(g=b.$eval(a)),e()})}}}]),angular.module("cryptClientApp").run(["$templateCache",function(a){a.put("views/about.html","<p>This is the about view.</p>"),a.put("views/documents.html","<div ng-cloak> Documents </div>"),a.put("views/friends.html","Friends"),a.put("views/groups.html",'<div ng-cloak> <h3> registered to Groups : </h3> <tab data="my_groups"></tab> <h3> Public Groups : </h3> <tab data="groups"></tab> </div>'),a.put("views/groups/mygroups.html",'<div> <h3> my groups </h3> <tab data="my_groups"></tab> </div>'),a.put("views/main.html",'<div class="jumbotron"> <h1>\'Allo, \'Allo!</h1> <p class="lead"> <img src="images/yeoman.png" alt="I\'m Yeoman"><br> Always a pleasure scaffolding your apps. </p> <p><a class="btn btn-lg btn-success" ng-href="#/">Splendid!<span class="glyphicon glyphicon-ok"></span></a></p> </div> <div class="row marketing"> <h4>HTML5 Boilerplate</h4> <p> HTML5 Boilerplate is a professional front-end template for building fast, robust, and adaptable web apps or sites. </p> <h4>Angular</h4> <p> AngularJS is a toolset for building the framework most suited to your application development. </p> <h4>Karma</h4> <p>Spectacular Test Runner for JavaScript.</p> </div>'),a.put("views/menu.html",'<div> <div layout="row"> <div flex>First item in row</div> <div flex="20">Second item in row</div> </div> <div layout="column"> <div flex="66">First item in column</div> <div flex="33">Second item in column</div> </div> </div>'),a.put("views/session/login.html",""),a.put("views/session/register.html",""),a.put("views/users.html",'<div ng-cloak> <h3> System Users </h3> <tab data="users"> </tab> </div> <div ui-view> </div>')}]);