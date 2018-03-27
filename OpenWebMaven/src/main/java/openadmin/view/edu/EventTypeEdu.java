package openadmin.view.edu;

/**
 * See https://www.w3schools.com/jsref/dom_obj_event.asp
 * @author eduard
 *
 */
public enum EventTypeEdu {
	//1. mouse events
	onclick,
	oncontextmenu,     //when the user right-clicks on an element to open a context menu
	ondblclick,
	onmousedown,
	onmouseenter,
	onmouseleave,
	onmousemove,
	onmouseover,
	onmouseout,
	onmouseup,
	
	//2.Keyboard events
	onkeydown,
	onkeypress,
	onkeyup,
	
	//3. Frame/Objects events
	onabort,			//when the loading of a resource has been aborted	2
	onbeforeunload,		//before the document is about to be unloaded	2
	onerror,			//when an error occurs while loading an external file	2
	onhashchange,		//when there has been changes to the anchor part of a URL	3
	onload,				//when an object has loaded	2
	onpageshow,			//when the user navigates to a webpage	3
	onpagehide,			//when the user navigates away from a webpage	3
	onresize,			//when the document view is resized	2
	onscroll,			//when an element's scrollbar is being scrolled	2
	onunload,			//once a page has unloaded (for <body>)	
	
	//4. Form events
	onblur, 	       //when an element loses focus	2
	onchange,	       //when the content of a form element, the selection, or the checked state have changed (for <input>, <select>, and <textarea>)	2
	onfocus,	       //when an element gets focus	2
	onfocusin,	       //when an element is about to get focus	2
	onfocusout,	       //when an element is about to lose focus	2
	oninput,	       //when an element gets user input	3
	oninvalid,	       //when an element is invalid	3
	onreset,	       //when a form is reset	2
	onsearch,	       //when the user writes something in a search field (for <input="search">)	3
	onselect,	       //after the user selects some text (for <input> and <textarea>)	2
	onsubmit,	       //when a form is submitted
	
	//5. Drag Events
	ondrag,            //when an element is being dragged	3
	ondragend,         //when the user has finished dragging an element	3
	ondragenter,       //when the dragged element enters the drop target	3
	ondragleave,       //when the dragged element leaves the drop target	3
	ondragover,        //when the dragged element is over the drop target	3
	ondragstart,       //when the user starts to drag an element	3
	ondrop,            //when the dragged element is dropped on the drop target	3

	//6. Clipboard Events
	oncopy,	           //when the user copies the content of an element	 
	oncut,	           //when the user cuts the content of an element	 
	onpaste,	       //when the user pastes some content in an element	 

	//7. Print Events
	onafterprint,	   //when a page has started printing, or if the print dialogue box has been closed	3
	onbeforeprint,	   //when a page is about to be printed	3

	//8. Media Events
	//onabort,	       //when the loading of a media is aborted	3
	oncanplay,	       //when the browser can start playing the media (when it has buffered enough to begin)	3
	oncanplaythrough,  //when the browser can play through the media without stopping for buffering	3
	ondurationchange,  //when the duration of the media is changed	3
	onemptied,	       //when something bad happens and the media file is suddenly unavailable (like unexpectedly disconnects)	3
	onended,	       //when the media has reach the end (useful for messages like "thanks for listening")	3
	//onerror,	       //when an error occurred during the loading of a media file	3
	onloadeddata,	   //when media data is loaded	3
	onloadedmetadata,  //when meta data (like dimensions and duration) are loaded	3
	onloadstart,       //when the browser starts looking for the specified media	3
	onpause,           //when the media is paused either by the user or programmatically	3
	onplay,	           //when the media has been started or is no longer paused	3
	onplaying,	       //when the media is playing after having been paused or stopped for buffering	3
	onprogress,	       //when the browser is in the process of getting the media data (downloading the media)	3
	onratechange,	   //when the playing speed of the media is changed	3
	onseeked,	       //when the user is finished moving/skipping to a new position in the media	3
	onseeking,	       //when the user starts moving/skipping to a new position in the media	3
	onstalled,	       //when the browser is trying to get media data, but data is not available	3
	onsuspend,	       //when the browser is intentionally not getting media data	3
	ontimeupdate,	   //when the playing position has changed (like when the user fast forwards to a different point in the media)	3
	onvolumechange,	   //when the volume of the media has changed (includes setting the volume to "mute")	3
	onwaiting,	       //when the media has paused but is expected to resume (like when the media pauses to buffer more data)	3
	
	// 9.Animation Events
	animationend,	   //when a CSS animation has completed	3
	animationiteration,//when a CSS animation is repeated	3
	animationstart,	   //when a CSS animation has started	3
	
	//10.Transition Events
	transitionend,	   //when a CSS transition has completed	3
	
	//11.Server-Sent Events
	//onerror,	       //when an error occurs with the event source	 
	onmessage,	       //when a message is received through the event source	 
	onopen,	           //when a connection with the event source is opened	 
	
	
	//12. Misc Events
	//onmessage,	       //when a message is received through or from an object (WebSocket, Web Worker, Event Source or a child frame or a parent window)	3
	onmousewheel,	   //Deprecated. Use the onwheel event instead	 
	ononline,	       //when the browser starts to work online	3
	onoffline,	       //when the browser starts to work offline	3
	onpopstate,	       //when the window's history changes	3
	onshow,	           //when a <menu> element is shown as a context menu	3
	onstorage,	       //when a Web Storage area is updated	3
	ontoggle,	       //when the user opens or closes the <details> element	3
	onwheel,	       //when the mouse wheel rolls up or down over an element	3
	
	//13.Touch Events
	ontouchcancel,	   //when the touch is interrupted	 
	ontouchend,	       //when a finger is removed from a touch screen	 
	ontouchmove,	   //when a finger is dragged across the screen	 
	ontouchstart	   //when a finger is placed on a touch screen	
	
}
