function update_query_parameters(key, val) {
   uri = window.location.href
      .replace(RegExp("([?&]" + key + "(?=[=&#]|$)[^#&]*|(?=#|$))"), "&" + key + "=" + encodeURIComponent(val))
      .replace(/^([^?&]+)&/, "$1?");
   return uri;
}
if (window.location.href.indexOf("IF_SOMETHING_YOU_WANT_EXISTS_IN_THE_URL_DONT_DO_A_THING") > -1) {
} else {
   window.location = update_query_parameters('KEY', 'VALUE');
}




