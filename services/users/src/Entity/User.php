<?php

declare(strict_types=1);

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Ramsey\Uuid\UuidInterface;

/**
 * @ORM\Entity(repositoryClass="App\Repository\UserRepository")
 * @ORM\Table(name="users", uniqueConstraints={@ORM\UniqueConstraint(name="username",columns={"username"})})
 */
class User
{
    /**
     * @ORM\Id()
     * @ORM\Column(type="uuid", unique=true)
     * @ORM\CustomIdGenerator(class="Ramsey\Uuid\Doctrine\UuidGenerator")
     * @ORM\GeneratedValue(strategy="CUSTOM")
     */
    private UuidInterface $uuid;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private string $username;

    public function __construct(UuidInterface $uuid, string $username)
    {
        $this->uuid = $uuid;
        $this->username = $username;
    }

    public static function builder(): UserBuilder
    {
        return new UserBuilder();
    }

    public function getUuid(): UuidInterface
    {
        return $this->uuid;
    }
}
