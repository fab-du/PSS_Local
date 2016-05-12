
Spring Boot
-----------

Spring Controller POST Request function signature
=================================================

```java
       
   public ResponseEntity<LinkedHashMap<String, String> 
      functionName( @RequestBody Map<String, String ){

      }
```

```java 

ResponseEntity<LinkedHashMap<String, String>> simplePost( Map<String, String> postObject ){

}

```

Solution 1

$scope.saveJSON = function () {
			$scope.toJSON = '';
			$scope.toJSON = angular.toJson($scope.data);
			var blob = new Blob([$scope.toJSON], { type:"application/json;charset=utf-8;" });			
			var downloadLink = angular.element('<a></a>');
                        downloadLink.attr('href',window.URL.createObjectURL(blob));
                        downloadLink.attr('download', 'fileName.json');
			downloadLink[0].click();
		};

<a class="btn" ng-click="saveJSON()" ng-href="{{ url }}">Export to JSON</a>


Solution 2

<a download="content.txt" ng-href="{{ url }}">download</a>

in your controller:

var content = 'file content for example';
var blob = new Blob([ content ], { type : 'text/plain' });
$scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
in order to enable the URL:

app = angular.module(...);
app.config(['$compileProvider',
    function ($compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
}]);

