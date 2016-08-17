var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

var UserSchema = new Schema({
	firstName:String,
	lastName:String,
	email:{
		type:String,
		index:true,
		match:[/.+\@.+\..+/,"Please fill a valid email address"]
	},
	username:{
	  type:String,
	  trim:true,
	  unique:true,
	  required: "UserName is required"
	},
	password:{
		type:String,
		validate:[
		function(password){
			return password && password.length > 6;	
		},
		'password should be longer'
		]
	},
	salt:{
		type:String
	},
	provider:{
		type:String,
		required:"Provider is required"
	},
	provideId:String,
	providerData:{},
	created:{
		type:Date,
		default:Date.now
	},
	website:{
		type:String,
		set:function(url){
			if(!url){
				return url;
			}else{
				if(url.indexOf('http://') == -1 && url.indexOf('https://') == -1 ){
					url = "http://"+url;
				}
				return url;
			}
		},
		get:function(url){
			if(!url){
				return url;
			}else{
				if(url.indexOf('http://') == -1 && url.indexOf('https://') == -1 ){
					url = "http://"+url;
				}
				return url;
			}
		}
	}
});





UserSchema.virtual('fullName').get(function(){
	return this.firstName+' '+this.lastName;
}).set(function(fullName){
	var splitName = fullName.split(' ');
	this.firstName = splitName[0] || '';
	this.lastName = splitName[1] || '';
});

UserSchema.pre('save',function(next){
	if(this.password){
		this.salt = new Buffer(crypto.randomBytes(16).toString('base64'),'base64');
		this.password = this.hashPassword(this.password);
	}
	next();
});


UserSchema.statics.findUniqueUsername = function(username,suffix,callback){
	var _this = this;
	var possibleUserName = username + (suffix || '');

	_this.findOne({
		username:possibleUserName
	},function(err,user){
		if(!err){
			if(!user){
				callback(possibleUserName);
			}else{
				return _this.findUniqueUsername(username,(suffix || 0)+1,callback);
			}
		}else{
			callback(null);
		}
	});
};

UserSchema.statics.findOneByUserName = function(username,callback){
	users.findOne({username: new RegExp(username,'i')},callback);
};


UserSchema.methods.hashPassword = function(password){
	return crypto.pdkdf2Sync(password,this.salt,10000,64).toString('base64');
};

UserSchema.methods.authenticate = function(password){
	return this.password === this.hashPassword(password);
};

UserSchema.set('toJSON',{getters:true,virtuals:true});

mongoose.model('User',UserSchema);