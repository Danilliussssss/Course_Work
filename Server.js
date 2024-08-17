const http = require('http');
http.createServer(function(req,res){
  let message
  
  res.setHeader("Content-Type","text/plain; charset=UTF-8")
  //res.setHeader("Content-Type","image/png")
  if(req.method === "POST")
  {
    message = ' '
    
  req.on('data',(chunk)=>{
    message = chunk.toString()
    
  });
  req.on('end',(chunk)=>{
    //console.log(message)
    res.write(message)
    res.end()
  });
  }
  else {
    res.write("Не POST-запрос");
    res.end()
  }
  console.log(res.toString())
  
}).listen(3500)