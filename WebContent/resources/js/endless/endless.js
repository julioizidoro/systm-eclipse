
$(function	()	{

	// Cookie validation
	if(jQuery.type($.cookie('skin_color')) != 'undefined')	{
	
		$('aside').removeClass('skin-1');
		$('aside').removeClass('skin-2');
		$('aside').removeClass('skin-3');
		$('aside').removeClass('skin-4');
		$('aside').removeClass('skin-5');
		$('aside').removeClass('skin-6');
		$('#top-nav').removeClass('skin-1');
		$('#top-nav').removeClass('skin-2');
		$('#top-nav').removeClass('skin-3');
		$('#top-nav').removeClass('skin-4');
		$('#top-nav').removeClass('skin-5');
		$('#top-nav').removeClass('skin-6');
		
		$('aside').addClass($.cookie('skin_color'));
		$('#top-nav').addClass($.cookie('skin_color'));
	}
	
	//Skin color
	$('.theme-color').click(function()	{
		//Cookies for storing theme color
		$.cookie('skin_color', $(this).attr('id'));
		
		$('aside').removeClass('skin-1');
		$('aside').removeClass('skin-2');
		$('aside').removeClass('skin-3');
		$('aside').removeClass('skin-4');
		$('aside').removeClass('skin-5');
		$('aside').removeClass('skin-6');
		$('#top-nav').removeClass('skin-1');
		$('#top-nav').removeClass('skin-2');
		$('#top-nav').removeClass('skin-3');
		$('#top-nav').removeClass('skin-4');
		$('#top-nav').removeClass('skin-5');
		$('#top-nav').removeClass('skin-6');
		
		$('aside').addClass($(this).attr('id'));
		$('#top-nav').addClass($(this).attr('id'));
	});
	
	// Delete values stored in cookies 
	// uncomment code to activate
	//	$.removeCookie('skin_color');
	//
	
	//Preloading
	paceOptions = {
		startOnPageLoad: true,
		ajax: false, // disabled
		document: false, // disabled
		eventLag: false, // disabled
		elements: false
	};
	
	//
	$('.login-link').click(function(e) {
		e.preventDefault();
		href = $(this).attr('href');
		
		$('.login-wrapper').addClass('fadeOutUp');

		setTimeout(function() {
			window.location = href;
		}, 900);
			
		return false;	
	});
	
	//Logout Confirmation
	$('#logoutConfirm').popup({
		pagecontainer: '.container',
		 transition: 'all 0.3s'
	});
	    
	//scroll to top of the page
	$("#scroll-to-top").click(function()	{
		$("html, body").animate({ scrollTop: 0}, 150);
		 return false;
	});
	
	//scrollable sidebar
	$('.scrollable-sidebar').slimScroll({   
		height: '100%',    
		size: '8px'      
	});   
	  
	//Sidebar menu dropdown
	$('aside li').hover(
       function(){ $(this).addClass('open') },
       function(){ $(this).removeClass('open') }
	)
	
	//Collapsible Sidebar Menu      
	$('.openable > a').click(function()	{	
		if(!$('#wrapper').hasClass('sidebar-mini'))	{
			if( $(this).parent().children('.submenu').is(':hidden') ) {
				$(this).parent().siblings().removeClass('open').children('.submenu').slideUp();
				$(this).parent().addClass('open').children('.submenu').slideDown();
			}
			else	{
				$(this).parent().removeClass('open').children('.submenu').slideUp();
			}
		}
		
		return false;
	});    
	         
}); 
    