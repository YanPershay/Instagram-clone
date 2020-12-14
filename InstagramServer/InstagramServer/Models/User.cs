using System;
using System.Collections.Generic;

namespace InstagramServer.Models
{
    public partial class User
    {
        public Guid UserId { get; set; }
        public string Username { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }

    }
}
